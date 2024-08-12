package mobile.wsmb2024.a08_module2

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import mobile.wsmb2024.a08_module2.screen.Login
import mobile.wsmb2024.a08_module2.screen.Profile
import mobile.wsmb2024.a08_module2.screen.Register
import mobile.wsmb2024.a08_module2.screen.Ride
import mobile.wsmb2024.a08_module2.viewModel.RideViewModel
import mobile.wsmb2024.a08_module2.viewModel.UserViewModel

enum class Navigate() { //Rider
    Register,
    Login,
    Ride,  //Home
    RideHistory,
    Profile
}

@Composable
fun Navigation(
    userViewModel: UserViewModel = viewModel(),
    rideViewModel: RideViewModel = viewModel()
) {
    val navController = rememberNavController()
    val db = Firebase.firestore


    NavHost(
        navController = navController,
        startDestination = Navigate.Ride.name
    ) {
        composable(route = Navigate.Login.name) {
            Login(userViewModel, navController)
        }
        composable(route = Navigate.Register.name) {
            Register(navController = navController, userViewModel = userViewModel)
        }
        composable(route = Navigate.Ride.name) {
            Ride(
                navController = navController,
                userViewModel = userViewModel,
                rideViewModel = rideViewModel
            )
        }
        composable(route = Navigate.Profile.name) {
            Profile(userViewModel = userViewModel, navController = navController)
        }
    }

}