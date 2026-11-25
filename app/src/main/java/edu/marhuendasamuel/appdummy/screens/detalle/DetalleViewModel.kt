package edu.marhuendasamuel.appdummy.screens.detalle

import edu.marhuendasamuel.appdummy.data.repository.LibrosRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import edu.marhuendasamuel.appdummy.AppDummyApplication
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DetalleViewModel(
    private val libroId: String,
    private val repository: LibrosRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow<DetalleUiState>(DetalleUiState.Cargando)
    val uiState: StateFlow<DetalleUiState> = _uiState.asStateFlow()

    init {
        cargarDetalle()
    }

    private fun cargarDetalle() {
        viewModelScope.launch {
            val libro = repository.obtenerPorId(libroId)
            _uiState.value = if (libro != null) DetalleUiState.Exito(libro)
            else DetalleUiState.NoEncontrado
        }
    }

    companion object {
        fun factoryConId(id: String): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val app = checkNotNull(
                    this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]
                ) as AppDummyApplication
                DetalleViewModel(
                    libroId = id,
                    repository = app.container.librosRepository
                )
            }
        }
    }
}