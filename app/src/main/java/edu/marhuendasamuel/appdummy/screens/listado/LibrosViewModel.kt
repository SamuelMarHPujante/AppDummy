package edu.marhuendasamuel.appdummy.screens.listado

import androidx.lifecycle.ViewModel
import edu.marhuendasamuel.appdummy.data.repository.LibrosRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class LibrosViewModel(
    private val repository: LibrosRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow<LibrosUiState>(LibrosUiState.Cargando)
    val uiState: StateFlow<LibrosUiState> = _uiState.asStateFlow()

    private val _busqueda = MutableStateFlow("")
    val busqueda: StateFlow<String> = _busqueda.asStateFlow()

    private val _autores = MutableStateFlow(listOf("Todos"))
    val autores: StateFlow<List<String>> = _autores.asStateFlow()

    private val _autorSeleccionado = MutableStateFlow("Todos")
    val autorSeleccionado: StateFlow<String> = _autorSeleccionado.asStateFlow()

    init {
        cargarLibros()
        cargarAutores()
    }

    private fun cargarLibros() {
        TODO("Not yet implemented")
    }

    private fun cargarAutores() {
        TODO("Not yet implemented")
    }
}
