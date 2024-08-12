package mobile.wsmb2024.a08_module2.screen

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Animation
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import kotlinx.coroutines.launch
import mobile.wsmb2024.a08_module2.Navigate
import mobile.wsmb2024.a08_module2.uiState.RideUiState
import mobile.wsmb2024.a08_module2.uiState.UserUiState
import mobile.wsmb2024.a08_module2.viewModel.RideViewModel
import mobile.wsmb2024.a08_module2.viewModel.UserViewModel
import java.text.DateFormat
import java.text.ParsePosition
import java.text.SimpleDateFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Ride(
    navController: NavController,
    userViewModel: UserViewModel,
    rideViewModel: RideViewModel
) {

    LaunchedEffect(key1 = true) {
        rideViewModel.retrieveRide()
    }


    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    ModalNavigationDrawer(
        drawerContent = {
            ModalDrawerSheet {
                Text("Menu", modifier = Modifier.padding(16.dp), fontSize = 24.sp)
                NavigationDrawerItem(
                    icon = {
                        Icon(
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = "Profile"
                        )
                    },
                    label = { Text(text = "My Profile", fontSize = 16.sp) },
                    selected = false,
                    onClick = { navController.navigate(Navigate.Profile.name) }
                )
                NavigationDrawerItem(
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Animation,
                            contentDescription = "My Ride"
                        )
                    },
                    label = { Text(text = "My Profile", fontSize = 16.sp) },
                    selected = false,
                    onClick = { navController.navigate(Navigate.Profile.name) }
                )
                NavigationDrawerItem(
                    icon = {
                        Icon(
                            imageVector = Icons.Default.History,
                            contentDescription = "Profile"
                        )
                    },
                    label = { Text(text = "All Ride History", fontSize = 16.sp) },
                    selected = false,
                    onClick = {
                        navController.navigate(Navigate.RideHistory.name)
                    }
                )
                NavigationDrawerItem(
                    icon = {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.Logout,
                            contentDescription = "Profile"
                        )
                    },
                    label = { Text(text = "Logout", fontSize = 16.sp) },
                    selected = false,
                    onClick = {
                        navController.navigate(Navigate.RideHistory.name)
                    }
                )
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color(177, 231, 255, 205),
                        titleContentColor = Color.Black
                    ),
                    title = { Text(text = "Kongsi Kereta") },
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch {
                                drawerState.apply {
                                    if (isClosed) open() else close()
                                }
                            }
                        }) {
                            Icon(
                                imageVector = Icons.Filled.Menu,
                                contentDescription = ""
                            )
                        }
                    })
            }
        ) {
            Column(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
            ) {
                var ride = RideUiState()
                var rideId: String = "rideId"
                var driver: UserUiState = UserUiState()
                var date: String = "date"
                var time: String = "time"
                var origin: String = "origin"
                var destination: String = "des"
                var fare: String = "fare"

                var rider: UserUiState = UserUiState()
                var riderList: ArrayList<UserUiState> = ArrayList<UserUiState>()


                ElevatedCard(
                    onClick = {},
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                ) {
                    Row {
                        AsyncImage(
                            modifier = Modifier.size(124.dp),
                            contentScale = ContentScale.Crop,

                            model = "https://firebasestorage.googleapis.com/v0/b/module-7c2fc.appspot.com/o/profile%2F0409260605.jpg?alt=media&token=bf1d096f-5e89-4e46-8647-4de674234fc1",
                            contentDescription = "",
                        )
                        Column(
                            modifier = Modifier
                                .padding(8.dp)
                                .fillMaxWidth()
                        ) {
                            Text(
                                text = "Driver Name: ${ride.driver.name}",
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Text(
                                text = "Origin: ${ride.origin}", maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Text(
                                text = "Destination: ${ride.destination}", maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Text(
                                text = "Date: ${ride.date} ${ride.time}", maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Text(
                                text = "1",
//                                text = "Remaining Seat: ${ride.driver.capacity.toInt()- ride.riderList.size - 1 } ",
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )

                        }
                    }
                    Button(
                        modifier = Modifier
                            .wrapContentHeight()
                            .fillMaxWidth()
                            .padding(8.dp),
                        shape = RoundedCornerShape(10),
                        onClick = { /*TODO*/ }) {
                        Text(text = "Join Ride")
                    }
                }
            }
        }
    }
}