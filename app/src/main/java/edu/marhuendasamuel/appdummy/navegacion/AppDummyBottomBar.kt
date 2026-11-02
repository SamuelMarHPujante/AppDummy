package edu.marhuendasamuel.appdummy.navegacion

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun AppDummyBottomBar(navController: NavController) {
    val navbackStackEntry by navController.currentBackStackEntryAsState()
    val destinoActual = navbackStackEntry?.destination
    val mostrarBottomBar = ItemsNavegacionPrincipal.any { item ->
        destinoActual?.hierarchy?.any { it.hasRoute(item.ruta::class) } == true
    }

    if (mostrarBottomBar) {
        NavigationBar {
            ItemsNavegacionPrincipal.forEach { item ->
                val seleccionado = destinoActual?.hierarchy?.any {
                    it.hasRoute(item.ruta::class)
                } == true

                NavigationBarItem(
                    selected = seleccionado,
                    onClick = {
                        navController.navigate(item.ruta) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    icon = {
                        Icon(
                            imageVector = if (seleccionado) item.iconoSeleccionado else item.icono,
                            contentDescription = item.etiqueta
                        )
                    },
                    label = { Text(item.etiqueta) }
                )
            }
        }
    }
}