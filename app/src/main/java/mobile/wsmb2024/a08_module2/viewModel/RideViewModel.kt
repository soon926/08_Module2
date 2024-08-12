package mobile.wsmb2024.a08_module2.viewModel

import android.icu.text.DateFormat
import android.icu.text.SimpleDateFormat
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import mobile.wsmb2024.a08_module2.uiState.RideUiState
import mobile.wsmb2024.a08_module2.uiState.UserUiState
import mobile.wsmb2024.a08_module2.uiState.cancel
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date

import java.util.UUID

class RideViewModel : ViewModel() {
    private val _rideUiState = MutableStateFlow(RideUiState())
    val rideUiState: StateFlow<RideUiState> = _rideUiState.asStateFlow()

    var db = Firebase.firestore


    var driver by mutableStateOf<UserUiState>(UserUiState())
    var date by mutableStateOf("")
    var time by mutableStateOf("")
    var origin by mutableStateOf("")
    var destination by mutableStateOf("")
    var fare by mutableStateOf("")
    var userData by mutableStateOf<UserUiState>(UserUiState())

    var showDate by mutableStateOf(false)
    var showTime by mutableStateOf(false)

    var rideList by mutableStateOf(ArrayList<RideUiState>())

    var rideListActive by mutableStateOf(ArrayList<RideUiState>())
    var rideListJoined by mutableStateOf(ArrayList<RideUiState>())
    var rideListCompleted by mutableStateOf(ArrayList<RideUiState>())
    var rideListCancelled by mutableStateOf(ArrayList<RideUiState>())

    fun createNewRide() {
        var collection = db.collection("Rides")
        var rideId = UUID.randomUUID().toString()
        var ride = RideUiState(
            rideId,
            driver,
            date,
            time,
            origin,
            destination,
            fare
        )
        collection.add(ride).addOnSuccessListener {

        }


    }

    fun retrieveRide() = CoroutineScope(Dispatchers.IO).launch {

        var collection = db.collection("Rides")
        var querySnapshot = collection.get().await()

        for (ride in querySnapshot.documents) {
            val ride = ride.toObject<RideUiState>()
            rideList.add(ride!!)
        }
    }

    fun retrieveRideCancelled() = CoroutineScope(Dispatchers.IO).launch {
        var collection = db.collection("Cancel")
        var querySnapshot = collection.get().await()

        for (cancel in querySnapshot.documents) {
            val cancel = cancel.toObject<cancel>()

            if (cancel != null) {
                if (userData.userId == cancel.user.userId) {
                    rideListCancelled.add(cancel.ride)

                }
            }
        }
    }


    fun retrieveRideJoined() = CoroutineScope(Dispatchers.IO).launch {
        var collection = db.collection("Rides")
        var querySnapshot = collection.get().await()
        for (ride in querySnapshot.documents) {
            val ride = ride.toObject<RideUiState>()

            val date = LocalDate.parse(ride!!.date, DateTimeFormatter.ofPattern("dd/MM/yyyy"))
            val time = LocalTime.parse(ride.time, DateTimeFormatter.ofPattern("HH:mm"))

            for (rider in ride.riderList) {
                if (userData.userId == rider.userId) {
                    if (date <= LocalDate.now()) {
                        if (time <= LocalTime.now()) {
                            Log.d("test", "${ride.rideId}")
                            rideListCompleted.add(ride!!)
                        } else {
                            rideListJoined.add(ride)
                        }
                    }
                }
            }
        }
    }

    fun retrieveRideActive() = CoroutineScope(Dispatchers.IO).launch {

        var collection = db.collection("Rides")
        var querySnapshot = collection.get().await()

        for (ride in querySnapshot.documents) {
            val ride = ride.toObject<RideUiState>()

            val date = LocalDate.parse(ride!!.date, DateTimeFormatter.ofPattern("dd/MM/yyyy"))
            val time = LocalTime.parse(ride.time, DateTimeFormatter.ofPattern("HH:mm"))

            if (date >= LocalDate.now()) {
                if (time >= LocalTime.now()) {
                    Log.d("test", "${ride.rideId}")
                    rideListActive.add(ride!!)
                }

            }
        }
    }

    fun joinRide(ride: RideUiState, userData: UserUiState) {
        var collection = db.collection("Rides")
        var rideList = ride.riderList
        rideList.add(userData)
        ride.riderList = rideList
        collection.document(ride.rideId).set(ride)
    }

    fun cancelRide(ride: RideUiState, userData: UserUiState) {
        Log.d("test", "Run")

        var collection = db.collection("Rides")
        var newRide = RideUiState()
        var rideList = newRide.riderList
        for (rider in ride.riderList) {
            if (rider.userId != userData.userId) {
                rideList.add(rider)
            }
        }
        ride.riderList = rideList
        collection.document(ride.rideId).set(ride)

        val map = mapOf(
            "user" to userData,
            "ride" to ride
        )
        db.collection("Cancel").add(map)
    }
}
