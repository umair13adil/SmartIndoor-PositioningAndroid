package com.cubivue.inlogic.ui.locate

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cubivue.inlogic.data.repositories.room.RoomRepository
import com.cubivue.inlogic.model.accessPoint.AccessPoint
import com.cubivue.inlogic.model.enums.AccessPointLocation
import com.cubivue.inlogic.model.enums.SignalStrengths
import com.cubivue.inlogic.model.room.Room
import com.cubivue.inlogic.utils.InDoorLocationHelper
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
    private val isInRoomResult = MutableLiveData<Pair<Room, Boolean>>()

    val rooms: LiveData<List<Room>>
        get() = listOfRooms

    val isInRoom: LiveData<Pair<Room, Boolean>>
        get() = isInRoomResult

    fun getSavedRooms() {
        val savedRooms = repository.getSavedRooms()

        if (savedRooms.isNullOrEmpty()) {
            Log.i(TAG, "getSavedRooms: Fetching from db")
            repository.appDatabase.roomDao().getRooms().observeForever {
                Log.i(TAG, "getSavedRooms: Fetched from db: ${it.size}")
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
