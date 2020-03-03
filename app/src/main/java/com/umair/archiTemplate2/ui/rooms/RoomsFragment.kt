package com.umair.archiTemplate2.ui.rooms

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
import com.blackbox.plog.pLogs.PLog
import kotlinx.android.synthetic.main.fragment_rooms.*
import javax.inject.Inject


class RoomsFragment : BaseFragment() {

    private val TAG = "RoomsFragment"

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: RoomsViewModel
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
            ViewModelProviders.of(this, viewModelFactory).get(RoomsViewModel::class.java)

        setUpListAdapter()

        viewModel.getSavedRooms().observe(viewLifecycleOwner, Observer {
            PLog.logThis(TAG, "getSavedRooms","Fetched: ${it.size}")

            if(it.isEmpty()){
                showNoRoomsLayout()
            }else {
                showRoomsLayout()

                listOfRooms.clear()
                listOfRooms.addAll(it)

                adapter.submitList(listOfRooms)
                adapter.notifyDataSetChanged()
            }
        })

        btn_room_mapper?.setOnClickListener {
            view.findNavController().navigate(R.id.roomMapperFragment)
        }
    }

    private fun showNoRoomsLayout(){
        layout_no_room.visibility = View.VISIBLE
        list_rooms.visibility = View.GONE
        txt_results.visibility = View.GONE
    }

    private fun showRoomsLayout(){
        layout_no_room.visibility = View.GONE
        list_rooms.visibility = View.VISIBLE
        txt_results.visibility = View.VISIBLE
    }

    /*
    * Setup RecyclerView list adapter.
    */
    private fun setUpListAdapter() {
        adapter = RoomsAdapter(::onConfirmDelete)

        layoutManager = GridLayoutManager(context, 2)

        list_rooms.layoutManager = layoutManager
        list_rooms.adapter = adapter
    }

    private fun onConfirmDelete(roomId: String) {
        PLog.logThis(TAG, "onConfirmDelete","$roomId")
        viewModel.deleteRoom(roomId)
    }
}
