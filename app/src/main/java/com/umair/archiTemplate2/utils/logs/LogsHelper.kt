package com.umair.archiTemplate2.utils.logs

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Environment
import androidx.core.content.ContextCompat.checkSelfPermission
import com.blackbox.plog.pLogs.PLog
import com.blackbox.plog.pLogs.config.LogsConfig
import com.blackbox.plog.pLogs.events.EventTypes
import com.blackbox.plog.pLogs.formatter.TimeStampFormat
import com.blackbox.plog.pLogs.models.LogExtension
import com.blackbox.plog.pLogs.models.LogLevel
import com.blackbox.plog.pLogs.models.LogType
import com.blackbox.plog.pLogs.structure.DirectoryStructure
import com.umair.archiTemplate2.BuildConfig
import java.io.File


object LogsHelper {

    private val TAG = "LogsHelper"

    private fun getLogsPath(): String {
        return File(Environment.getExternalStorageDirectory(), "archiTemplate2").path
    }

    private fun getLogsExportPath(): String {
        return getLogsPath() + File.separator + "Exported" + File.separator
    }

    private fun getLogsConfiguration(context: Context): LogsConfig {

        val logsPath = getLogsPath()
        val zipName = "archiTemplate2"

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
}