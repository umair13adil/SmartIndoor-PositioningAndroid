package com.cubivue.inlogic.components

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.wifi.ScanResult
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.cubivue.inlogic.R
import com.cubivue.inlogic.data.db.AppDatabase
import com.cubivue.inlogic.data.db.AppExecutors
import com.cubivue.inlogic.model.accessPoint.AccessPoint
import com.cubivue.inlogic.ui.accessPoint.AccessPointActivity
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
        startRunningInForeground()
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

                AppExecutors.instance?.diskIO()?.execute {
                    val listOfRooms = appDatabase.roomDao().getSavedRooms()

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

    private fun startRunningInForeground() {

        //if more than or equal to 26
        if (Build.VERSION.SDK_INT >= 26) {

            //if more than 26
            if (Build.VERSION.SDK_INT > 26) {
                val CHANNEL_ONE_ID = "Package.Service"
                val CHANNEL_ONE_NAME = "Screen service"
                var notificationChannel: NotificationChannel? = null
                notificationChannel = NotificationChannel(
                    CHANNEL_ONE_ID,
                    CHANNEL_ONE_NAME, NotificationManager.IMPORTANCE_MIN
                )
                notificationChannel.enableLights(true)
                notificationChannel.lightColor = Color.RED
                notificationChannel.setShowBadge(true)
                notificationChannel.lockscreenVisibility = Notification.VISIBILITY_PUBLIC
                val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                manager.createNotificationChannel(notificationChannel)

                val icon = BitmapFactory.decodeResource(resources, R.drawable.ic_directions_run_black_24dp)
                val notification = Notification.Builder(applicationContext,"3")
                    .setChannelId(CHANNEL_ONE_ID)
                    .setContentTitle("Recording data")
                    .setContentText("App is running background operations")
                    .setSmallIcon(R.drawable.ic_directions_run_black_24dp)
                    .setLargeIcon(icon)
                    .build()

                val notificationIntent = Intent(applicationContext, AccessPointActivity::class.java)
                notificationIntent.flags =
                    Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                notification.contentIntent =
                    PendingIntent.getActivity(applicationContext, 0, notificationIntent, 0)

                startForeground(101, notification)
            } else {
                startForeground(101, updateNotification())
            }//if version 26
        } else {
            val notification = NotificationCompat.Builder(this,"1")
                .setContentTitle("App")
                .setContentText("App is running background operations")
                .setSmallIcon(R.drawable.ic_directions_run_black_24dp)
                .setOngoing(true).build()

            startForeground(101, notification)
        }//if less than version 26
    }

    private fun updateNotification(): Notification {

        val pendingIntent = PendingIntent.getActivity(
            this, 0,
            Intent(this, AccessPointActivity::class.java), 0
        )

        return NotificationCompat.Builder(this,"2")
            .setContentTitle("Activity log")
            .setTicker("Ticker")
            .setContentText("app is running background operations")
            .setSmallIcon(R.drawable.ic_directions_run_black_24dp)
            .setContentIntent(pendingIntent)
            .setOngoing(true).build()
    }
}
