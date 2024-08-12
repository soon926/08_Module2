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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import java.time.LocalDate
import java.time.LocalTime
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Ride(
    navController: NavController,
    userViewModel: UserViewModel,
    rideViewModel: RideViewModel
) {

    LaunchedEffect(key1 = true) {
        rideViewModel.rideListActive.clear()
        rideViewModel.retrieveRideActive()

    }
    rideViewModel.userData = userViewModel.userData

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    ModalNavigationDrawer(
        drawerState = drawerState,
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
                            imageVector = Icons.Default.History,
                            contentDescription = "His"
                        )
                    },
                    label = { Text(text = "Ride History", fontSize = 16.sp) },
                    selected = false,
                    onClick = {
                        navController.navigate(Navigate.RideHistory.name)
                    }
                )
                NavigationDrawerItem(
                    icon = {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.Logout,
                            contentDescription = "Logout"
                        )
                    },
                    label = { Text(text = "Logout", fontSize = 16.sp) },
                    selected = false,
                    onClick = {
                        userViewModel.signOut()
                        navController.navigate(Navigate.Login.name)
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

                LazyColumn {
                    items(rideViewModel.rideListActive) {
                        ElevatedCard(
                            onClick = {/*TODO*/ },
                            modifier = Modifier
                                .padding(8.dp)
                                .fillMaxWidth()
                        ) {
                            Row {
                                AsyncImage(
                                    modifier = Modifier.size(124.dp),
                                    contentScale = ContentScale.Crop,

                                    model = it.driver.photo,
                                    contentDescription = "",
                                )
                                Column(
                                    modifier = Modifier
                                        .padding(8.dp)
                                        .fillMaxWidth()
                                ) {
                                    Text(
                                        text = "Driver Name: ${it.driver.name}",
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                    Text(
                                        text = "Origin: ${it.origin}", maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                    Text(
                                        text = "Destination: ${it.destination}", maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                    Text(
                                        text = "Date: ${it.date} ${it.time}", maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                    Text(
                                        text = "",
//                                        text = "Remaining Seat: ${it.driver.capacity.toInt() - it.riderList.size - 1} ",
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
                                onClick = {
                                    rideViewModel.joinRide(
                                        ride = it,
                                        userViewModel.userData
                                    )
                                }) {
                                Text(text = "Join Ride")

                            }
                        }
                    }
                }
            }
        }
    }
}