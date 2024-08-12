package mobile.wsmb2024.a08_module2.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Animation
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
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
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
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
import mobile.wsmb2024.a08_module2.viewModel.RideViewModel
import mobile.wsmb2024.a08_module2.viewModel.UserViewModel
import java.time.LocalTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RideHistory(
    userViewModel: UserViewModel,
    rideViewModel: RideViewModel,
    navController: NavController
) {
    LaunchedEffect(key1 = true) {
        rideViewModel.rideListCompleted.clear()
        rideViewModel.rideListJoined.clear()
        rideViewModel.rideListCancelled.clear()

        rideViewModel.retrieveRideJoined()
        rideViewModel.retrieveRideCancelled()


    }
    rideViewModel.userData = userViewModel.userData
    var state by remember { mutableStateOf(0) }
    val titles = listOf("Active", "Inactive", "Cancelled")
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
                            imageVector = Icons.Default.Home,
                            contentDescription = "Home"
                        )
                    },
                    label = { Text(text = "Home", fontSize = 16.sp) },
                    selected = false,
                    onClick = {
                        navController.navigate(Navigate.Ride.name)
                    }
                )
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
                    title = { Text(text = "Ride History") },
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
                TabRow(selectedTabIndex = state) {
                    titles.forEachIndexed { index, title ->
                        Tab(
                            selected = state == index,
                            onClick = { state = index },
                            text = {
                                Text(
                                    text = title,
                                    maxLines = 2,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        )
                    }
                }

                when (state + 1) {
                    1 -> {
                        LazyColumn {
                            items(rideViewModel.rideListJoined) {
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
                                                text = "Destination: ${it.destination}",
                                                maxLines = 1,
                                                overflow = TextOverflow.Ellipsis
                                            )
                                            Text(
                                                text = "Date: ${it.date} ${it.time}", maxLines = 1,
                                                overflow = TextOverflow.Ellipsis
                                            )
                                            Text(
                                                text = "${LocalTime.now()}",
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
                                        onClick = {
                                            rideViewModel.cancelRide(
                                                ride = it,
                                                userViewModel.userData
                                            )
                                        }) {
                                        Text(text = "Cancel Ride")

                                    }
                                }
                            }
                        }
                    }

                    2 -> {
                        LazyColumn {
                            items(rideViewModel.rideListCompleted) {
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
                                                text = "Destination: ${it.destination}",
                                                maxLines = 1,
                                                overflow = TextOverflow.Ellipsis
                                            )
                                            Text(
                                                text = "Date: ${it.date} ${it.time}", maxLines = 1,
                                                overflow = TextOverflow.Ellipsis
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }

                    3 -> {
                        LazyColumn {
                            items(rideViewModel.rideListCancelled) {
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
                                                text = "Destination: ${it.destination}",
                                                maxLines = 1,
                                                overflow = TextOverflow.Ellipsis
                                            )
                                            Text(
                                                text = "Date: ${it.date} ${it.time}", maxLines = 1,
                                                overflow = TextOverflow.Ellipsis
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun RideActiveCard(rideList: Any, rideViewModel: RideViewModel, userViewModel: UserViewModel) {
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
                            text = "${LocalTime.now()}",
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
                    onClick = {
                        rideViewModel.cancelRide(
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