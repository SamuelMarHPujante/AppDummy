package edu.marhuendasamuel.appdummy.navegacion

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.toRoute
import androidx.navigation.compose.rememberNavController
import edu.marhuendasamuel.appdummy.screens.listado.PantallaListado
import edu.marhuendasamuel.appdummy.screens.detalle.PantallaDetalle
import edu.marhuendasamuel.appdummy.screens.favoritos.PantallaFavoritos

@Composable
fun AppNavigation(
    navController: NavHostController = rememberNavController()
) {
    Scaffold(
        bottomBar = {
            AppDummyBottomBar(navController = navController)
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Listado,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable<Listado> {
                PantallaListado(
                    onNavegaADetalle = { id ->
                        navController.navigate(Detalle(id = id))
                    }
                )
            }

            composable<Detalle> { backStackEntry ->
                val ruta: Detalle = backStackEntry.toRoute<Detalle>()
                PantallaDetalle(
                    libroId = ruta.id,
                    onVolver = { navController.navigateUp() }
                )
            }

            composable<Favoritos> {
                PantallaFavoritos(
                    onNavegaADetalle = { id ->
                        navController.navigate(Detalle(id = id))
                    }
                )
            }
        }
    }
}