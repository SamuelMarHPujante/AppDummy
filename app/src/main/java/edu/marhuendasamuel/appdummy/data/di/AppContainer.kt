package edu.marhuendasamuel.appdummy.data.di

import android.content.Context
import edu.marhuendasamuel.appdummy.data.datasource.local.AppDatabase
import edu.marhuendasamuel.appdummy.data.datasource.local.LocalDataSource
import edu.marhuendasamuel.appdummy.data.repository.LibrosRepository

interface AppContainer {
    val librosRepository: LibrosRepository
}

class DefaultAppContainer(context: Context) : AppContainer {
    private val database by lazy { AppDatabase.getDatabase(context) }
    private val localDataSource: LocalDataSource by lazy { LocalDataSource(database.libroDao()) }
    override val librosRepository: LibrosRepository by lazy { LibrosRepository(localDataSource) }
}