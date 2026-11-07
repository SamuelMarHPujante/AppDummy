package edu.marhuendasamuel.appdummy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import edu.marhuendasamuel.appdummy.navegacion.AppNavigation
import edu.marhuendasamuel.appdummy.ui.theme.AppDummyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppDummyTheme {
                AppNavigation()
            }
        }
    }
}