package edu.marhuendasamuel.appdummy.data.datasource.local

import androidx.room.*
import edu.marhuendasamuel.appdummy.data.model.Genero
import edu.marhuendasamuel.appdummy.data.model.GeneroConLibros
import kotlinx.coroutines.flow.Flow
import edu.marhuendasamuel.appdummy.data.model.Libro
import edu.marhuendasamuel.appdummy.data.model.LibroGenero

@Dao
interface LibrosDao {
    // -- Consultas reactivas --
    @Query("SELECT * FROM libros ORDER BY year DESC")
    fun observarTodos(): Flow<List<Libro>>

    @Query("SELECT * FROM libros WHERE es_favorito = 1 ORDER BY titulo ASC")
    fun observarFavoritos(): Flow<List<Libro>>

    @Query("SELECT * FROM libros WHERE titulo LIKE '%' || :busqueda || '%' ORDER BY year DESC")
    fun observarPorTitulo(busqueda: String): Flow<List<Libro>>

    @Query("SELECT * FROM libros WHERE id = :id")
    fun observarPorId(id: String): Flow<Libro?>


    // -- Consultas puntuales --
    @Query("SELECT * FROM libros WHERE id = :id")
    suspend fun obtenerPorId(id: String): Libro?

    @Query("SELECT DISTINCT autor FROM libros ORDER BY autor ASC")
    suspend fun obtenerAutores(): List<String>?


    // -- Escritura --
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertarIgnorado(libros: List<Libro>): List<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarReemplazando(libro: Libro)

    @Upsert
    suspend fun upsert(libro: Libro)

    @Update
    suspend fun actualizar(libro: Libro)

    @Delete
    suspend fun eliminar(libro: Libro)

    @Query("""
        UPDATE libros SET
            titulo = :titulo,
            autor = :autor,
            year = :year,
            isbn = :isbn,
            cover = :cover
        WHERE id = :id
    """)
    suspend fun actualizarDesdeRed(
        id: String,
        titulo: String,
        autor: String,
        year: Int?,
        isbn: String,
        cover: String?
    )

    @Query("UPDATE libros SET es_favorito = NOT es_favorito WHERE id = :id")
    suspend fun toggleFavorito(id: String)

    @Query("UPDATE libros SET leido = NOT leido WHERE id = :id")
    suspend fun toggleLeido(id: String)

    @Transaction
    suspend fun upsertConservandoFavorito(libros: List<Libro>) {
        val resultados = insertarIgnorado(libros)
        libros.forEachIndexed { index, libro ->
            if (resultados[index] == -1L) {
                actualizarDesdeRed(
                    id = libro.id,
                    titulo = libro.titulo,
                    autor = libro.autor,
                    year = libro.year,
                    isbn = libro.isbn,
                    cover = libro.cover
                )
            }
        }
    }
}

@Dao
interface GeneroDao {
    @Transaction
    @Query("SELECT * FROM generos ORDER BY nombre ASC")
    fun observarGenerosConLibros(): Flow<List<GeneroConLibros>>

    @Transaction
    @Query("SELECT * FROM generos WHERE generoID = :id")
    fun observarGeneroConLibros(id: Int): Flow<GeneroConLibros?>

    @Upsert
    suspend fun insertarGenero(genero: Genero)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarLibros(libros: List<LibroGenero>)
}
