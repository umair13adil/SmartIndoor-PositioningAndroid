package com.cubivue.inlogic.model.enums

object SignalStrengths {

    fun getRouterLocations(level: Int): AccessPointLocation {
        return when (level) {
            in -32..0 -> AccessPointLocation.VERY_NEAR
            in -40..-32 -> AccessPointLocation.CLOSE
            in -55..-40 -> AccessPointLocation.MIDDLE
            in -60..-55 -> AccessPointLocation.AWAY
            in -67..-60 -> AccessPointLocation.FURTHER_AWAY
            in -70..-67 -> AccessPointLocation.FAR
            in -80..-70 -> AccessPointLocation.NOT_IN_RANGE
            else -> AccessPointLocation.DISAPPEARED
        }
    }
}