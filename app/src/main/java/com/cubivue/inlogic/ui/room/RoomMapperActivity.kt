package com.cubivue.inlogic.ui.room

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.cubivue.inlogic.R
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_room_mapper.*
import javax.inject.Inject

class RoomMapperActivity : DaggerAppCompatActivity() {

    private val TAG = "RoomMapperActivity"

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: RoomMapperViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel =
            ViewModelProviders.of(this, viewModelFactory).get(RoomMapperViewModel::class.java)

        setContentView(R.layout.activity_room_mapper)

        viewModel.accessPoints.observe(this, Observer {
            it.forEach {
                Log.i(TAG, "${it.name}")
            }
        })

        select_ap_1.setOnClickListener {
            viewModel.getAccessPoints()
        }
    }
}
