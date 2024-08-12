package mobile.wsmb2024.a08_module2.screen

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import mobile.wsmb2024.a08_module2.viewModel.UserViewModel

@Composable
fun Profile(userViewModel: UserViewModel, navController: NavController) {

    LaunchedEffect(key1 = true) {

    }

    var isValid by remember { (mutableStateOf(false)) }
    var isError by remember { (mutableStateOf(true)) }
    var isEdit by remember { (mutableStateOf(false)) }
    var userViewModel = userViewModel.userData
    var password by remember {
        mutableStateOf("")
    }

    if (isValid) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            ElevatedCard(
                colors = CardDefaults.elevatedCardColors(
                    contentColor = Color.Black,
                    containerColor = Color.White,
                    disabledContentColor = Color.White,
                    disabledContainerColor = Color.White
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    AsyncImage(
                        contentScale = ContentScale.Crop,
                        model = userViewModel.photo, contentDescription = "profile",
                        modifier = Modifier
                            .size(150.dp)
                            .clip(RoundedCornerShape(50))
                            .border(1.dp, Color.Black, CircleShape)
                    )
                    OutlinedTextField(
                        enabled = isEdit,
                        label = { Text(text = "Name") },
                        value = userViewModel.name,
                        onValueChange = { userViewModel.name = it })
                    OutlinedTextField(
                        enabled = isEdit,
                        label = { Text(text = "IC") },
                        value = userViewModel.ic,
                        onValueChange = { userViewModel.ic = it })
                    OutlinedTextField(
                        enabled = isEdit,
                        label = { Text(text = "Email") },
                        value = userViewModel.email,
                        onValueChange = { userViewModel.email = it })
                    OutlinedTextField(
                        enabled = isEdit,

                        label = { Text(text = "Gender") },
                        value = userViewModel.gender,
                        onValueChange = { userViewModel.gender = it })
                    OutlinedTextField(
                        enabled = isEdit,
                        label = { Text(text = "Phone") },
                        value = userViewModel.phone,
                        onValueChange = { userViewModel.phone = it })
                    OutlinedTextField(
                        enabled = isEdit,
                        label = { Text(text = "Address") },
                        value = userViewModel.address,
                        onValueChange = { userViewModel.address = it })
                }
            }
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(56.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Enter Password For Verification")
            OutlinedTextField(
                isError = !isError,
                supportingText = {
                    if (!isError) {
                        Text(text = "Wrong Password!", color = Color.Red)
                    }
                },
                label = { Text(text = "Password") },
                value = password, onValueChange = { password = it })
            Button(
                modifier = Modifier
                    .wrapContentHeight()
                    .fillMaxWidth()
                    .padding(0.dp),
                shape = RoundedCornerShape(10),
                onClick = {
                    if (password == userViewModel.password) {
                        isValid = true
                        isError = true
                    } else {
                        isValid = false
                        isError = false
                    }
                }) {
                Text(text = "Submit")
            }

        }
    }
}