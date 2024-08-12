package mobile.wsmb2024.a08_module2.viewModel

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.storage.storage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import mobile.wsmb2024.a08_module2.Navigate
import mobile.wsmb2024.a08_module2.uiState.UserUiState

class UserViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(UserUiState())
    val uiState: StateFlow<UserUiState> = _uiState.asStateFlow()

    var currentStep by mutableStateOf(1)

    var userId by mutableStateOf("")
    var photo by mutableStateOf("")
    var ic by mutableStateOf("")
    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var name by mutableStateOf("")
    var gender by mutableStateOf("")
    var phone by mutableStateOf("")
    var address by mutableStateOf("")
    var isDriver by mutableStateOf(false)

    var photoLink by mutableStateOf("")
    var errorLogin by mutableStateOf("")

    var userData by mutableStateOf(UserUiState())

    var db = Firebase.firestore
    var auth = Firebase.auth

    var loginErr by mutableStateOf("")

    fun uploadImage(uri: Uri) = CoroutineScope(Dispatchers.IO).launch {
        var storage = Firebase.storage
        var storageRef = storage.reference
        var photoName = ic
        Log.d("test", photoName)

        var spaceRef = storageRef.child("profile/${photoName}.jpg")

        var uploadTask = spaceRef.putFile(uri)
        uploadTask.addOnSuccessListener {
            it.storage.downloadUrl.addOnSuccessListener {
                photoLink = it.toString()
                Log.d("test", "Upload Image $photoLink")
            }
        }
    }

    fun registerUser() {
        uploadImage(photo.toUri())
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                val userData = UserUiState(
                    auth.currentUser?.uid ?: "",
                    photoLink,
                    ic,
                    email,
                    password,
                    name,
                    gender,
                    phone,
                    address,
                    isDriver = false,
                )
                db.collection("User").document(auth.currentUser?.uid ?: "").set(userData)
            }
        }
        Firebase.auth.signOut()
    }


    fun getUserData(userId: String) = CoroutineScope(Dispatchers.IO).launch {
        val userRef =
            db.collection("User").document(userId).get().await()
        if (userRef.exists()) {
            val user = userRef.toObject<UserUiState>()
            user?.let { userData = it }
        }
    }

    fun signIn(navController: NavController) {

        if (email != "" && password != "") {
            auth.signInWithEmailAndPassword(email, password)
                .addOnFailureListener {
                    errorLogin = "Wrong Email/Password."
                    Log.d("test", "fail")

                }
                .addOnSuccessListener {
                    navController.navigate(Navigate.Ride.name)
                }
        } else {
            errorLogin = "Wrong Email/Password."
        }
    }

    fun signOut() {
        auth.signOut()
    }

}
