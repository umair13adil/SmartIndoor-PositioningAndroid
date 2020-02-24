package com.cubivue.inlogic.ui.accessPoint

import android.net.wifi.ScanResult
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.cubivue.inlogic.R
import com.cubivue.inlogic.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_access_points.*
import javax.inject.Inject

open class AccessPointFragment() : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: AccessPointViewModel

    private val TAG = "AccessPointFragment"

    //List
    private lateinit var adapter: AccessPointsAdapter
    private lateinit var layoutManager: LinearLayoutManager


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_access_points, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setBackButton(false)

        viewModel =
            ViewModelProviders.of(this, viewModelFactory).get(AccessPointViewModel::class.java)

        setUpListAdapter()

        btn_room_mapper.setOnClickListener {
            view.findNavController().navigate(R.id.roomMapperFragment)
        }

        btn_show_rooms.setOnClickListener {
            view.findNavController().navigate(R.id.roomsFragment)
        }

        btn_locate_me.setOnClickListener {
            view.findNavController().navigate(R.id.locateFragment)
        }
    }


    /*
     * Setup RecyclerView list adapter.
     */
    private fun setUpListAdapter() {
        adapter = AccessPointsAdapter()

        layoutManager = LinearLayoutManager(context)

        list_access_points.layoutManager = layoutManager
        list_access_points.adapter = adapter
    }

    fun doOnResults(results: List<ScanResult>) {
        progress_circular?.visibility = View.GONE

        viewModel.accessPoints.clear()
        adapter.submitList(viewModel.accessPoints)

        val sorted = viewModel.addAccessPointsToList(results)

        adapter.submitList(sorted)
        adapter.notifyDataSetChanged()
    }
}
