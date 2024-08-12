package mobile.wsmb2024.a08_module2.screen

import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedCard
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import mobile.wsmb2024.a08_module2.uiState.RideUiState

@Composable
fun RideDetail(ride: RideUiState) {
    Column(modifier = Modifier.padding(8.dp)) {
      Row()  {
            ElevatedCard(

                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
            ) {
                AsyncImage(
                    contentScale = ContentScale.Crop,
                    model = ride.driver.photo, contentDescription = "profile",
                    modifier = Modifier
                        .size(150.dp)
                        .clip(RoundedCornerShape(50))
                        .border(1.dp, Color.Black, CircleShape)
                )
            }
        }
    }
}