package edu.marhuendasamuel.appdummy.screens.favoritos

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import edu.marhuendasamuel.appdummy.screens.common.MuestraLibros
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaFavoritos(
    viewModel: FavoritosViewModel = viewModel(factory = FavoritosViewModel.Factory),
    onNavegaADetalle: (Int) -> Unit = {}
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    var snackbarJob by remember { mutableStateOf<Job?>(null) }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val busqueda by viewModel.busqueda.collectAsStateWithLifecycle()
    val autorSeleccionado by viewModel.autorSeleccionado.collectAsStateWithLifecycle()
    val autores by viewModel.autores.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.eventos.collect { evento ->
            when (evento) {
                is LibrosEvento.MostrarSnackbar -> {
                    snackbarJob?.cancel()
                    snackbarHostState.currentSnackbarData?.dismiss()
                    snackbarJob = scope.launch {
                        snackbarHostState.showSnackbar(evento.mensaje)
                    }
                }

                is LibrosEvento.NavegarADetalle -> {  /* navController.navigate(ruta tipada — ver T3) */
                }
            }
        }
    }

    MuestraLibros(
        busqueda = busqueda,
        onBusquedaChanged = viewModel::actualizarBusqueda,
        autores = autores,
        autorSeleccionado = autorSeleccionado,
        onAutorSelected = viewModel::actualizarAutorSeleccionado,
        uiState = uiState,
        onNavegaADetalle = onNavegaADetalle,
        onToggleLeido = { viewModel.toggleLeido(it) },
        onToggleFavorito = { viewModel.toggleFavorito(it) },
        onReintentar = viewModel::cargarLibros,
        snackbarHostState = snackbarHostState
    )
}

@Preview(showBackground = true)
@Composable
fun PantallaFavoritosPreview() {
    MaterialTheme {
        PantallaFavoritos()
    }
}
