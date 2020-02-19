package com.cubivue.inlogic.utils

import com.cubivue.inlogic.model.enums.AccessPointPosition

fun getAccessPointPosition(position: Int): AccessPointPosition {
    when (position) {
        1 -> return AccessPointPosition.ACCESS_POINT_TOP_LEFT
        2 -> return AccessPointPosition.ACCESS_POINT_TOP_RIGHT
        3 -> return AccessPointPosition.ACCESS_POINT_BOTTOM_LEFT
        4 -> return AccessPointPosition.ACCESS_POINT_BOTTOM_RIGHT
    }
    return AccessPointPosition.ACCESS_POINT_UNDEFINED
}