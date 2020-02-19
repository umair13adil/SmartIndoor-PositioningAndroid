package com.cubivue.inlogic.ui.room

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.cubivue.inlogic.R
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class RoomMapperActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: RoomMapperViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel =
            ViewModelProviders.of(this, viewModelFactory).get(RoomMapperViewModel::class.java)

        setContentView(R.layout.activity_room_mapper)
    }
}
