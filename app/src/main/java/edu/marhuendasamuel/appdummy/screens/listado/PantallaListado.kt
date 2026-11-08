package edu.marhuendasamuel.appdummy.screens.listado

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import edu.marhuendasamuel.appdummy.screens.common.MuestraLibros

@Composable
fun PantallaListado(
    viewModel: LibrosViewModel = viewModel(factory = LibrosViewModel.Factory),
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
                is LibrosEvento.NavegarADetalle -> { /* navController.navigate(ruta tipada — ver T3 )*/ }
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
fun PantallaListadoPreview() {
    MaterialTheme {
        PantallaListado()
    }
}
