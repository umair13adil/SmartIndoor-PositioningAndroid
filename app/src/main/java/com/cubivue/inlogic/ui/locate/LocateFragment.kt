package com.cubivue.inlogic.ui.locate

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.cubivue.inlogic.R
import com.cubivue.inlogic.model.room.Room
import com.cubivue.inlogic.ui.base.BaseFragment
import com.cubivue.inlogic.ui.rooms.RoomsAdapter
import com.embrace.plog.pLogs.PLog
import dagger.android.support.DaggerAppCompatActivity
import dagger.android.support.DaggerFragment
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
            PLog.logThis(TAG, "rooms","Fetched Rooms: ${it.size}")
            listOfRooms.clear()
            listOfRooms.addAll(it)

            adapter.submitList(listOfRooms)
            adapter.notifyDataSetChanged()
        })

        viewModel.getAccessPoints().observeForever {
            PLog.logThis(TAG, "getAccessPoints","Fetched Access Points: ${it.size}")
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
