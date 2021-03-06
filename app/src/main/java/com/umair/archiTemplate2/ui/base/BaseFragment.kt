package com.umair.archiTemplate2.ui.base

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import dagger.android.support.DaggerFragment

open class BaseFragment : DaggerFragment() {

    private val TAG = "BaseFragment"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    fun setBackButton(show: Boolean = true) {
        val toolbar = (activity as AppCompatActivity).supportActionBar
        toolbar?.setHomeButtonEnabled(show)
        toolbar?.setDisplayHomeAsUpEnabled(show)
        toolbar?.title = view?.findNavController()?.currentDestination?.label
    }
}