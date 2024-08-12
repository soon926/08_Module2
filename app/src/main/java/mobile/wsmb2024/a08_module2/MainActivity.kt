package mobile.wsmb2024.a08_module2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import mobile.wsmb2024.a08_module2.ui.theme._08_Module2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            _08_Module2Theme {
                Navigation()
            }
        }
    }
}