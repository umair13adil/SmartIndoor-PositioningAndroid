package com.cubivue.inlogic.ui.rooms

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
import com.embrace.plog.pLogs.PLog
import dagger.android.support.DaggerAppCompatActivity
import dagger.android.support.DaggerFragment
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
            listOfRooms.clear()
            listOfRooms.addAll(it)

            adapter.submitList(listOfRooms)
            adapter.notifyDataSetChanged()
        })
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
