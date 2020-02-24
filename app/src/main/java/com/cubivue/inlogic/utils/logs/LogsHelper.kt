package com.cubivue.inlogic.utils.logs

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Environment
import androidx.core.content.ContextCompat.checkSelfPermission
import com.cubivue.inlogic.BuildConfig
import com.cubivue.inlogic.utils.DateTimeUtils
import com.embrace.plog.pLogs.PLog
import com.embrace.plog.pLogs.config.LogsConfig
import com.embrace.plog.pLogs.events.EventTypes
import com.embrace.plog.pLogs.formatter.TimeStampFormat
import com.embrace.plog.pLogs.models.LogExtension
import com.embrace.plog.pLogs.models.LogLevel
import com.embrace.plog.pLogs.models.LogType
import com.embrace.plog.pLogs.structure.DirectoryStructure
import java.io.File


object LogsHelper {

    private val TAG = "LogsHelper"

    private fun getLogsPath(): String {
        return File(Environment.getExternalStorageDirectory(), "InLogic").path
    }

    private fun getLogsExportPath(): String {
        return getLogsPath() + File.separator + "Exported" + File.separator
    }

    private fun getLogsConfiguration(context: Context): LogsConfig {

        val logsPath = getLogsPath()
        val zipName = "InLogic"

        return LogsConfig(
            logLevelsEnabled = arrayListOf(
                LogLevel.ERROR,
                LogLevel.SEVERE,
                LogLevel.INFO,
                LogLevel.WARNING
            ),
            logTypesEnabled = arrayListOf(
                LogType.Device.type
            ),
            logsRetentionPeriodInDays = 7, //Will not work if local XML config file is not present
            zipsRetentionPeriodInDays = 3, //Will not work if local XML config file is not present
            autoDeleteZipOnExport = false,
            autoClearLogs = true,
            autoExportErrors = true,
            encryptionEnabled = false,
            directoryStructure = DirectoryStructure.FOR_DATE,
            logSystemCrashes = true,
            isDebuggable = BuildConfig.DEBUG,
            debugFileOperations = false,
            attachTimeStamp = true,
            attachNoOfFiles = true,
            timeStampFormat = TimeStampFormat.TIME_FORMAT_READABLE,
            logFileExtension = LogExtension.LOG,
            zipFilesOnly = false,
            savePath = logsPath,
            zipFileName = "${zipName}_",
            exportPath = getLogsExportPath(),
            singleLogFileSize = 2, //2Mb
            enabled = true
        )
    }

    fun setUpPLogger(context: Context?) {

        context?.let {
            val config = getLogsConfiguration(context)

            config.also { it ->
                it.getLogEventsListener()
                    .doOnNext {
                        when (it.event) {
                            EventTypes.NEW_ERROR_REPORTED -> {

                            }
                            EventTypes.LOG_TYPE_EXPORTED -> {
                                PLog.logThis("PLogger", "log exported", it.data, LogLevel.INFO)
                            }
                            EventTypes.NEW_EVENT_DIRECTORY_CREATED -> {
                                PLog.logThis(
                                    "PLogger",
                                    "event",
                                    "New directory created: " + it.data,
                                    LogLevel.INFO
                                )
                            }
                            EventTypes.DELETE_EXPORTED_FILES -> {
                                PLog.logThis(
                                    "PLogger",
                                    "event",
                                    "Exported files will be deleted: " + it.data,
                                    LogLevel.INFO
                                )
                            }
                            else -> {
                                //PLog.logThis("PLogger", "event", "Logger Event: ${it.event}" + it.data, LogLevel.INFO)
                            }
                        }
                    }
                    .subscribe()
            }

            if (areStoragePermissionsGranted(context)) {

                //Apply Configurations
                PLog.applyConfigurations(config, saveToFile = true)
            }
        }
    }

    private fun areStoragePermissionsGranted(context: Context): Boolean {
        return (checkSelfPermission(
            context,
            Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
                && checkSelfPermission(
            context,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED)
    }

    fun writeLogToFile(type: String, data: String) {
        try {
            PLog.getLoggerFor(type)
                ?.appendToFile(data + " [${DateTimeUtils.getReadableTime(System.currentTimeMillis())}]")
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun overWriteLogToFile(type: String, data: String) {
        try {
            PLog.getLoggerFor(type).let {
                it?.overwriteToFile(data)
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }
}