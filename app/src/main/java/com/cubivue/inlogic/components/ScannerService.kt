package com.cubivue.inlogic.components

import android.app.Service
import android.content.Context
import android.content.Intent
import android.net.wifi.ScanResult
import android.os.IBinder
import com.cubivue.inlogic.data.db.AppDatabase
import com.cubivue.inlogic.data.db.AppExecutors
import com.cubivue.inlogic.model.accessPoint.AccessPoint
import com.cubivue.inlogic.utils.InDoorLocationHelper
import com.cubivue.inlogic.utils.WiFiScannerHelper
import com.embrace.plog.pLogs.PLog
import dagger.android.AndroidInjection
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ScannerService : Service() {

    private val TAG = "ScannerService"
    private lateinit var wiFiScannerHelper: WiFiScannerHelper

    @Inject
    lateinit var appDatabase: AppDatabase

    @Inject
    lateinit var locationHelper: InDoorLocationHelper

    override fun onCreate() {
        AndroidInjection.inject(this)
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        PLog.logThis(TAG, "onStartCommand", "Scanner Service Started.")
        setUpWifiScanner()
        return Service.START_NOT_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        PLog.logThis(TAG, "onDestroy", "Scanner Service Stopped.")
    }

    private fun setUpWifiScanner() {
        wiFiScannerHelper = WiFiScannerHelper(::doOnResults)
        wiFiScannerHelper.setupWifiManager(applicationContext, this)
        wiFiScannerHelper.startScanner(attachHandler = false)
    }

    private fun doOnResults(results: List<ScanResult>) {
        wiFiScannerHelper.unregisterReceiver(this)

        PLog.logThis(TAG, "doOnResults", "Received.")
        val accessPoints = arrayListOf<AccessPoint>()

        results.forEach { res ->
            accessPoints.add(
                AccessPoint(
                    ssid = res.BSSID,
                    name = res.SSID,
                    strength = res.level,
                    scanTime = res.timestamp
                )
            )
        }

        if(results.isNotEmpty()) {
            AppExecutors.instance?.diskIO()?.execute {
                appDatabase.accessPointDao().insertAll(accessPoints)
                PLog.logThis(TAG, "doOnResults", "AccessPoints Updated.")

                AppExecutors.instance?.diskIO()?.execute {
                    val listOfRooms = appDatabase.roomDao().getSavedRooms()
                    PLog.logThis(TAG, "doOnResults", "Rooms Fetched: ${listOfRooms.size}")

                    if (listOfRooms.isNotEmpty()) {
                        listOfRooms.forEach { room ->

                            locationHelper.isInTheRoom(room)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribeBy(
                                    onNext = { result ->
                                        listOfRooms.filter {
                                            it.roomId == room.roomId
                                        }.let {
                                            it.map {
                                                it.inHere = result.first
                                                it.assessment = result.second

                                                AppExecutors.instance?.diskIO()?.execute {
                                                    appDatabase.roomDao().insert(it)
                                                    stopSelf()
                                                }
                                            }
                                        }
                                    },
                                    onError = {
                                        it.printStackTrace()
                                        stopSelf()
                                    }
                                )
                        }
                    } else {
                        stopSelf()
                    }
                }
            }
        }else{
            stopSelf()
        }
    }
}
