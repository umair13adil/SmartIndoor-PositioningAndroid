package com.umair.archiTemplate2.ui.locate

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.umair.archiTemplate2.data.repositories.room.RoomRepository
import com.umair.archiTemplate2.model.accessPoint.AccessPoint
import com.umair.archiTemplate2.model.room.Room
import com.umair.archiTemplate2.utils.InDoorLocationHelper
import com.blackbox.plog.pLogs.PLog
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class LocateViewModel @Inject constructor(
    val context: Context,
    val repository: RoomRepository,
    val locationHelper: InDoorLocationHelper
) : ViewModel() {

    private val TAG = "LocateViewModel"

    private val listOfRooms = MutableLiveData<List<Room>>()
    private val isInRoomResult = MutableLiveData<Pair<Room, Pair<Boolean, String>>>()

    val rooms: LiveData<List<Room>>
        get() = listOfRooms

    val isInRoom: LiveData<Pair<Room, Pair<Boolean, String>>>
        get() = isInRoomResult

    fun getSavedRooms() {
        val savedRooms = repository.getSavedRooms()

        if (savedRooms.isNullOrEmpty()) {
            PLog.logThis(TAG, "getSavedRooms", "Fetching from db")
            repository.appDatabase.roomDao().getRooms().observeForever {
                PLog.logThis(TAG, "getSavedRooms", "Fetched from db: ${it.size}")
                repository.saveRooms(it)
                listOfRooms.postValue(it)
            }
        } else {
            listOfRooms.postValue(savedRooms)
        }
    }

    fun getAccessPoints(): LiveData<List<AccessPoint>> {
        return repository.appDatabase.accessPointDao().getAllAccessPoints()
    }

    fun locateMe() {
        repository.getSavedRooms()?.forEach { room ->

            locationHelper.isInTheRoom(room)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onNext = {
                        isInRoomResult.postValue(Pair(room, it))
                    },
                    onError = {
                        it.printStackTrace()
                    }
                )
        }
    }
}
