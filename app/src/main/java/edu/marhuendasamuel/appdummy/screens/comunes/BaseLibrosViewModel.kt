package edu.marhuendasamuel.appdummy.screens.comunes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.marhuendasamuel.appdummy.data.model.Libro
import edu.marhuendasamuel.appdummy.data.repository.LibrosRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

sealed class LibrosEvento {
    data class MostrarSnackbar(val mensaje: String) : LibrosEvento()
    data class NavegarADetalle(val id: Int) : LibrosEvento()
}


abstract class BaseLibrosViewModel(
    protected val repository: LibrosRepository,
    flujoLibros: Flow<List<Libro>>
) : ViewModel() {

    private val _busqueda = MutableStateFlow("")
    val busqueda: StateFlow<String> = _busqueda.asStateFlow()

    val uiState: StateFlow<LibrosUiState> = flujoLibros
        .map<List<Libro>, LibrosUiState> {
            cargarAutores()
            LibrosUiState.Exito(it)
        }
        .catch { error -> emit(LibrosUiState.Error(error.message ?: "Error")) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = LibrosUiState.Cargando
        )

    private val _autores = MutableStateFlow(listOf("Todos"))
    val autores: StateFlow<List<String>> = _autores.asStateFlow()

    private val _autorSeleccionado = MutableStateFlow("Todos")
    val autorSeleccionado: StateFlow<String> = _autorSeleccionado.asStateFlow()

    private val _eventos = MutableSharedFlow<LibrosEvento>(
        replay = 0,
        extraBufferCapacity = 1
    )
    val eventos: SharedFlow<LibrosEvento> = _eventos.asSharedFlow()

    fun cargarAutores() {
        viewModelScope.launch {
            val autores = repository.obtenerAutores() ?: emptyList()
            _autores.value = listOf("Todos") + autores
        }
    }

    fun actualizarBusqueda(texto: String) { _busqueda.value = texto }

    fun toggleFavorito(libro: Libro) {
        viewModelScope.launch {
            repository.toggleFavorito(libro.id)
            _eventos.emit(
                LibrosEvento.MostrarSnackbar(
                    if (libro.esFavorito) "\"${libro.titulo}\" eliminado de favoritos"
                    else "\"${libro.titulo}\" añadido a favoritos"
                )
            )
        }
    }

    fun toggleLeido(libro: Libro) {
        viewModelScope.launch {
            repository.toggleLeido(libro.id)
            _eventos.emit(
                LibrosEvento.MostrarSnackbar(
                    if (libro.leido) "\"${libro.titulo}\" marcado como no leído"
                    else "\"${libro.titulo}\" marcado como leído"
                )
            )
        }
    }

    fun actualizarAutorSeleccionado(autor: String) { _autorSeleccionado.value = autor }
}
