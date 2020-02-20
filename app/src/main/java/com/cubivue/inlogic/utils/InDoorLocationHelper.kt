package com.cubivue.inlogic.utils

import android.util.Log
import com.cubivue.inlogic.data.db.AppDatabase
import com.cubivue.inlogic.model.accessPoint.AccessPoint
import com.cubivue.inlogic.model.enums.AccessPointLocation
import com.cubivue.inlogic.model.enums.SignalStrengths
import com.cubivue.inlogic.model.room.Room
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.Observables
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
open class InDoorLocationHelper @Inject constructor(val appDatabase: AppDatabase) {

    private val TAG = "InDoorLocationHelper"

    fun isInTheRoom(room: Room): Observable<Boolean> {
        Log.i(TAG, "Checking if in room: ${room.roomName}")

        return Observables.combineLatest(
            getSignalStrengthOfAccessPoint(room.accessPointTopLeft),
            getSignalStrengthOfAccessPoint(room.accessPointTopRight),
            getSignalStrengthOfAccessPoint(room.accessPointBottomLeft),
            getSignalStrengthOfAccessPoint(room.accessPointBottomRight)
        ) { ap1, ap2, ap3, ap4 ->
            val locationsOfAccessPoints = arrayListOf<AccessPoint>()
            locationsOfAccessPoints.add(ap1)
            locationsOfAccessPoints.add(ap2)
            locationsOfAccessPoints.add(ap3)
            locationsOfAccessPoints.add(ap4)
            locationsOfAccessPoints
        }.flatMap {
            Log.i(TAG, "Results: ${it.size}")

            val locations = it.map {
                val location = SignalStrengths.getRouterLocations(it.strength)
                location
            }

            Log.i(TAG, "Location: $locations")
            return@flatMap Observable.just(locations)
        }.flatMap {
            var count = 0

            it.forEach {
                if (it == AccessPointLocation.CLOSE || it == AccessPointLocation.VERY_NEAR || it == AccessPointLocation.MIDDLE) {
                    count++
                }
            }

            return@flatMap Observable.just(count >= 3)
        }
    }

    private fun getSignalStrengthOfAccessPoint(accessPointName: String): Observable<AccessPoint> {
        return appDatabase.accessPointDao().findAccessPointByName(accessPointName)
    }
}