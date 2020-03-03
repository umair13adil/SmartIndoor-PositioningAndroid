package com.umair.archiTemplate2.utils

import com.umair.archiTemplate2.data.db.AppDatabase
import com.umair.archiTemplate2.model.accessPoint.AccessPoint
import com.umair.archiTemplate2.model.enums.AccessPointLocation
import com.umair.archiTemplate2.model.enums.SignalStrengths
import com.umair.archiTemplate2.model.room.Room
import com.blackbox.plog.pLogs.PLog
import io.reactivex.Observable
import io.reactivex.rxkotlin.Observables
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
open class InDoorLocationHelper @Inject constructor(val appDatabase: AppDatabase, val textToSpeech: TTSHelper) {

    private val TAG = "InDoorLocationHelper"

    fun isInTheRoom(room: Room): Observable<Pair<Boolean, String>> {
        var lastResult = ""

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

            val locations = it.map {
                val location = SignalStrengths.getRouterLocations(it.strength)
                location
            }

            //PLog.logThis(TAG, "assessments", "${room.roomName}: $locations")
            return@flatMap Observable.just(locations)
        }.flatMap {
            var count = 0

            it.forEach {
                if (it == AccessPointLocation.CLOSE || it == AccessPointLocation.VERY_NEAR || it == AccessPointLocation.MIDDLE) {
                    count++
                }
            }

            val result = Pair(count >= 3, it.joinToString(","))
            if (result.first) {
                val newResult = "${room.roomName}: ${result.first}"

                if (lastResult.isEmpty() || lastResult != newResult) {
                    PLog.logThis(TAG, "isInTheRoom", newResult)
                }

                lastResult = "${room.roomName}: ${result.first}"

                textToSpeech.speak(room.roomName)
            }

            return@flatMap Observable.just(result)
        }
    }

    private fun getSignalStrengthOfAccessPoint(accessPointName: String): Observable<AccessPoint> {
        return appDatabase.accessPointDao().findAccessPointByName(accessPointName)
    }
}