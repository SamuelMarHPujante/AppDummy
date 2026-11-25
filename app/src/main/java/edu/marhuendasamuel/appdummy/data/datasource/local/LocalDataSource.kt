package edu.marhuendasamuel.appdummy.data.datasource.local

import edu.marhuendasamuel.appdummy.data.model.Libro
import kotlinx.coroutines.flow.Flow

class LocalDataSource(private val dao: LibrosDao) {
    fun observarTodos(): Flow<List<Libro>> = dao.observarTodos()
    fun observarFavoritos(): Flow<List<Libro>> = dao.observarFavoritos()
    fun observarPorTitulo(busqueda: String): Flow<List<Libro>> = dao.observarPorTitulo(busqueda)
    fun observarPorId(id: String): Flow<Libro?> = dao.observarPorId(id)

    // Escritura

    suspend fun upsertconservandoFavorito(libros: List<Libro>) = dao.upsertConservandoFavorito(libros)
    suspend fun toggleFavorito(id: String) = dao.toggleFavorito(id)
    suspend fun toggleLeido(id: String) = dao.toggleLeido(id)
    suspend fun insertarIgnorado(libros: List<Libro>) = dao.insertarIgnorado(libros)
    suspend fun obtenerAutores(): List<String>? = dao.obtenerAutores()
    suspend fun obtenerPorId(id: String): Libro? = dao.obtenerPorId(id)
}