package com.cubivue.inlogic.ui.locate

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.cubivue.inlogic.R
import com.cubivue.inlogic.model.room.Room
import com.cubivue.inlogic.ui.rooms.RoomsAdapter
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_rooms.*
import javax.inject.Inject

class LocateActivity : DaggerAppCompatActivity() {

    private val TAG = "LocateActivity"

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: LocateViewModel
    private var listOfRooms = arrayListOf<Room>()

    //List
    private lateinit var adapter: RoomsAdapter
    private lateinit var layoutManager: GridLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel =
            ViewModelProviders.of(this, viewModelFactory).get(LocateViewModel::class.java)

        setContentView(R.layout.activity_rooms)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        setUpListAdapter()

        //viewModel.getAccessPoints()
        viewModel.getSavedRooms()

        viewModel.rooms.observe(this, Observer {
            Log.i(TAG, "Fetched Rooms: ${it.size}")
            listOfRooms.clear()
            listOfRooms.addAll(it)

            adapter.submitList(listOfRooms)
            adapter.notifyDataSetChanged()
        })

        viewModel.getAccessPoints().observeForever {
            Log.i(TAG, "Fetched Access Points: ${it.size}")
            viewModel.locateMe()
        }
    }

    /*
    * Setup RecyclerView list adapter.
    */
    private fun setUpListAdapter() {
        adapter = RoomsAdapter(::onDeleteSelection)

        layoutManager = GridLayoutManager(this, 2)

        list_rooms.layoutManager = layoutManager
        list_rooms.adapter = adapter
    }

    fun onDeleteSelection(id:String){

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
