package edu.marhuendasamuel.appdummy.data.repository
import edu.marhuendasamuel.appdummy.data.datasource.local.LocalDataSource
import edu.marhuendasamuel.appdummy.data.model.Libro
import kotlinx.coroutines.flow.Flow

class LibrosRepository(private val localDataSource: LocalDataSource) {
    fun observarLibros(): Flow<List<Libro>> = localDataSource.observarTodos()
    fun observarFavoritos(): Flow<List<Libro>> = localDataSource.observarFavoritos()
    fun observarPorId(id: String): Flow<Libro?> = localDataSource.observarPorId(id)

    suspend fun toggleFavorito(id: String) = localDataSource.toggleFavorito(id)
    suspend fun insertarIgnorando(libros: List<Libro>) = localDataSource.insertarIgnorado(libros)
    suspend fun obtenerAutores(): List<String>? = localDataSource.obtenerAutores()
    suspend fun obtenerPorId(id: String): Libro? = localDataSource.obtenerPorId(id)
}