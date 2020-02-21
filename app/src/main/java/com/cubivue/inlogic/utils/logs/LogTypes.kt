package com.cubivue.inlogic.utils.logs

import com.embrace.plog.pLogs.models.LogType

/**
 * Log types defined in app.
 */
enum class LogTypes(val type: String) {

    LOG_TYPE_NOTIFICATIONS("Notifications"),
    LOG_TYPE_PARSER("Parser"),
    LOG_TYPE_DELIVERY_LOCATION("Delivery_Location"),
    LOG_TYPE_DELIVERIES("Deliveries"),
    LOG_TYPE_GENERAL_INFO("General_Info"),
    LOG_TYPE_TRACKING("Tracking"),
    LOG_TYPE_NETWORK(LogType.Network.type),
    LOG_TYPE_LOCATION(LogType.Location.type),
    LOG_TYPE_DEVICE(LogType.Device.type),
    LOG_TYPE_DAMAGES("Damages"),
    LOG_TYPE_RECEIPTS("Receipts"),
    LOG_TYPE_DIAGNOSTICS_REPORT("DiagnosticsReport"),
    LOG_TYPE_ANALYTICS_REPORT("Analytics"),
    LOG_TYPE_NETWORK_EVENTS("NetworkEvents"),
    LOG_TYPE_JOB_INFO("JobInfo"),
    LOG_TYPE_RECEIPT_REQUESTS("ReceiptRequests"),
}