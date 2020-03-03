package com.umair.archiTemplate2.ui.locate

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.umair.archiTemplate2.R
import com.umair.archiTemplate2.model.room.Room
import com.umair.archiTemplate2.ui.base.BaseFragment
import com.umair.archiTemplate2.ui.rooms.RoomsAdapter
import com.blackbox.plog.pLogs.PLog
import kotlinx.android.synthetic.main.fragment_rooms.*
import javax.inject.Inject

class LocateFragment : BaseFragment() {

    private val TAG = "LocateFragment"

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: LocateViewModel
    private var listOfRooms = arrayListOf<Room>()

    //List
    private lateinit var adapter: RoomsAdapter
    private lateinit var layoutManager: GridLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_rooms, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setBackButton()

        viewModel =
            ViewModelProviders.of(this, viewModelFactory).get(LocateViewModel::class.java)

        setUpListAdapter()

        //viewModel.getAccessPoints()
        viewModel.getSavedRooms()

        viewModel.rooms.observe(viewLifecycleOwner, Observer {
            PLog.logThis(TAG, "rooms", "Fetched Rooms: ${it.size}")

            if (it.isEmpty()) {
                showNoRoomsLayout()
            } else {
                showRoomsLayout()

                listOfRooms.clear()
                listOfRooms.addAll(it)

                adapter.submitList(listOfRooms)
                adapter.notifyDataSetChanged()
            }
        })


        viewModel.getAccessPoints().observeForever {
            PLog.logThis(TAG, "getAccessPoints", "Fetched Access Points: ${it.size}")
            viewModel.locateMe()
        }

        viewModel.isInRoom.observeForever { result ->

            listOfRooms.filter {
                it.roomId == result.first.roomId
            }.let {
                it.map {
                    it.inHere = result.second.first
                    it.assessment = result.second.second
                }
            }

            adapter.submitList(listOfRooms)
            adapter.notifyDataSetChanged()
        }

        btn_room_mapper?.setOnClickListener {
            view.findNavController().navigate(R.id.roomMapperFragment)
        }
    }

    private fun showNoRoomsLayout() {
        layout_no_room.visibility = View.VISIBLE
        list_rooms.visibility = View.GONE
        txt_results.visibility = View.GONE
    }

    private fun showRoomsLayout() {
        layout_no_room.visibility = View.GONE
        list_rooms.visibility = View.VISIBLE
        txt_results.visibility = View.VISIBLE
    }

    /*
    * Setup RecyclerView list adapter.
    */
    private fun setUpListAdapter() {
        adapter = RoomsAdapter(::onDeleteSelection, isSimpleList = false)

        layoutManager = GridLayoutManager(context, 2)

        list_rooms.layoutManager = layoutManager
        list_rooms.adapter = adapter
    }

    private fun onDeleteSelection(id: String) {

    }
}
