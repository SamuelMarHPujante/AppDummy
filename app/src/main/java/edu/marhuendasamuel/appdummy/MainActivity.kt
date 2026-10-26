package edu.marhuendasamuel.appdummy

import androidx.activity.ComponentActivity
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import edu.marhuendasamuel.appdummy.screens.PantallaListado

class MainActivity : ComponentActivity() {
    @Preview(showBackground = true)
    @Composable
    fun PantallaBienvenidaPreview() {
        MaterialTheme {
            PantallaBienvenida(onEntrar = { })
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun PantallaListadoPreview() {
        MaterialTheme {
            PantallaListado()
        }
    }
}