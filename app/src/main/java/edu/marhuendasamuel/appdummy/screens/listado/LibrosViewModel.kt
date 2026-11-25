package edu.marhuendasamuel.appdummy.screens.listado

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import edu.marhuendasamuel.appdummy.AppDummyApplication
import edu.marhuendasamuel.appdummy.data.repository.LibrosRepository
import edu.marhuendasamuel.appdummy.screens.comunes.BaseLibrosViewModel
import kotlinx.coroutines.launch

class LibrosViewModel(
    repository: LibrosRepository
) : BaseLibrosViewModel(repository, repository.observarLibros()) {
    fun cargarLibros() {
        viewModelScope.launch {
            repository.insertarIgnorando(repository.getLibros())
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val app = checkNotNull(
                    this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]
                ) as AppDummyApplication
                LibrosViewModel(app.container.librosRepository)
            }
        }
    }
}
