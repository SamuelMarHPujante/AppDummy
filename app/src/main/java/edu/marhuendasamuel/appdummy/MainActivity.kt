package edu.marhuendasamuel.appdummy

import androidx.activity.ComponentActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import edu.marhuendasamuel.appdummy.navegacion.AppNavigation
import edu.marhuendasamuel.appdummy.ui.theme.AppDummyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppDummyTheme {
                AppNavigation()
            }
        }
    }
}