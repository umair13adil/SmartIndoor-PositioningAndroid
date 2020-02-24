package com.cubivue.inlogic.ui.roomMapper

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.cubivue.inlogic.R
import com.cubivue.inlogic.model.accessPoint.AccessPoint
import com.cubivue.inlogic.model.enums.AccessPointPosition
import com.cubivue.inlogic.utils.getAccessPointPosition
import com.embrace.plog.pLogs.PLog
import kotlinx.android.synthetic.main.dialog_access_point_selection.*

class AccessPointSelectionDialog : DialogFragment() {

    private val TAG = "AccessPointDialog"

    //List
    private lateinit var adapter: SingleSelectionAdapter
    private lateinit var layoutManager: StaggeredGridLayoutManager
    private var selected = ""
    private var position = -1

    private var callback: AccessPointSelectionListener? = null

    fun setOnAccessPointSelectionListener(callback: AccessPointSelectionListener) {
        this.callback = callback
    }

    interface AccessPointSelectionListener {
        fun onSelected(position: AccessPointPosition, selectedName: String)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.dialog_access_point_selection, container, true)
        isCancelable = true
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpListAdapter()

        arguments?.getParcelableArrayList<AccessPoint>(PARAM_LIST)?.let {
            val sorted = it.sortedByDescending { it.strength }
            adapter.submitList(sorted.map { it.name })
        }

        arguments?.getInt(PARAM_AP_POSITION)?.let {
            this.position = it
        }

        btn_select.setOnClickListener {
            if (selected.isNotEmpty()) {
                callback?.onSelected(getAccessPointPosition(position), selected)
                dismissAllowingStateLoss()
            }
        }
    }

    /*
     * Setup RecyclerView list adapter.
     */
    private fun setUpListAdapter() {
        adapter = SingleSelectionAdapter(::onSelection)

        layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        layoutManager.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_NONE

        list_access_points.layoutManager = layoutManager
        list_access_points.adapter = adapter
    }

    private fun onSelection(selected: String) {
        this.selected = selected
        PLog.logThis(TAG, "onSelection", "$selected")
        adapter.setSelection(selected)
        adapter.notifyDataSetChanged()
    }

    companion object {

        private val PARAM_AP_POSITION = "position"
        private val PARAM_LIST = "list"

        fun newInstance(
            accessPointPosition: Int,
            list: ArrayList<AccessPoint>
        ): AccessPointSelectionDialog {
            val fragment = AccessPointSelectionDialog()
            val args = Bundle()
            args.putInt(PARAM_AP_POSITION, accessPointPosition)
            args.putParcelableArrayList(PARAM_LIST, list)
            fragment.arguments = args
            return fragment
        }
    }
}
