package edu.marhuendasamuel.appdummy.screens.listado

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import edu.marhuendasamuel.appdummy.data.repository.LibrosRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.Call

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

    fun cargarLibros() {
        viewModelScope.launch {
            _uiState.value = LibrosUiState.Cargando
            try {
                val libros = repository.getLibros()
                _uiState.value = LibrosUiState.Exito(libros)
            } catch (e: Exception) {
                _uiState.value = LibrosUiState.Error(
                    e.message ?: "Error desconocido al cargar los libros"
                )
            }
        }
    }

    fun cargarAutores() {
        viewModelScope.launch {
            try {
                val autores = repository.getAutores()
                _autores.value = listOf("Todos") + autores
            } catch (e: Exception) {
                _uiState.value = LibrosUiState.Error(
                    e.message ?: "Error desconocido al cargar los autores"
                )
            }
        }
    }

    fun actualizarBusqueda(texto: String) {
        _busqueda.value = texto
    }

    fun toggleFavorito(id: Int) {
        _uiState.update { estado ->
            if (estado is LibrosUiState.Exito) {
                estado.copy(
                    libros = estado.libros.map { Libro ->
                        if (Libro.id == id) Libro.copy(esFavorito = !Libro.esFavorito)
                        else Libro
                    }
                )
            } else estado
        }
    }

    fun toggleLeido(id: Int) {
        _uiState.update { estado ->
            if (estado is LibrosUiState.Exito) {
                estado.copy(
                    libros = estado.libros.map { Libro ->
                        if (Libro.id == id) Libro.copy(leido = !Libro.leido)
                        else Libro
                    }
                )
            } else estado
        }
    }

    fun actualizarAutorSeleccionado(autor: String) {
        _autorSeleccionado.value = autor
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                LibrosViewModel(LibrosRepository())
            }
        }
    }
}
