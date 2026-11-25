package edu.marhuendasamuel.appdummy.screens.favoritos

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import edu.marhuendasamuel.appdummy.AppDummyApplication
import edu.marhuendasamuel.appdummy.data.repository.LibrosRepository
import edu.marhuendasamuel.appdummy.screens.comunes.BaseLibrosViewModel

class FavoritosViewModel(
    repository: LibrosRepository
) : BaseLibrosViewModel(repository, repository.observarFavoritos()) {
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val app = checkNotNull(
                    this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]
                ) as AppDummyApplication
                FavoritosViewModel(app.container.librosRepository)
            }
        }
    }
}