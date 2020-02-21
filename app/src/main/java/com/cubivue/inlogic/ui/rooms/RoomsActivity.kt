package com.cubivue.inlogic.ui.rooms

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.cubivue.inlogic.R
import com.cubivue.inlogic.model.room.Room
import com.embrace.plog.pLogs.PLog
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_rooms.*
import javax.inject.Inject


class RoomsActivity : DaggerAppCompatActivity() {

    private val TAG = "RoomsActivity"

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: RoomsViewModel
    private var listOfRooms = arrayListOf<Room>()

    //List
    private lateinit var adapter: RoomsAdapter
    private lateinit var layoutManager: GridLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel =
            ViewModelProviders.of(this, viewModelFactory).get(RoomsViewModel::class.java)

        setContentView(R.layout.activity_rooms)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        setUpListAdapter()

        viewModel.getSavedRooms().observe(this, Observer {
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

        layoutManager = GridLayoutManager(this, 2)

        list_rooms.layoutManager = layoutManager
        list_rooms.adapter = adapter
    }

    private fun onConfirmDelete(roomId: String) {
        PLog.logThis(TAG, "onConfirmDelete","$roomId")
        viewModel.deleteRoom(roomId)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
