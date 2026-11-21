package edu.marhuendasamuel.appdummy.data.model

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Relation
import androidx.room.Transaction
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Entity(
    tableName = "libros",
    indices = [
        Index(value = ["titulo"]),
        Index(value = ["autor", "isbn"], unique = false)
    ]
)
data class Libro(
    @PrimaryKey
    val id: String,

    @ColumnInfo(name = "titulo")
    val titulo: String,

    @ColumnInfo(name = "autor")
    val autor: String,

    @ColumnInfo(name = "year")
    val year: Int? = 1900,

    @ColumnInfo(name = "isbn")
    val isbn: String,

    @ColumnInfo(name = "cover")
    val cover: String? = null,

    @ColumnInfo(name = "es_favorito")
    val esFavorito: Boolean = false,

    @ColumnInfo(name = "leido")
    val leido: Boolean = false
)

@Entity(tableName = "generos")
data class Genero(
    @PrimaryKey val generoId: Int,
    val nombre: String,
    val descripcion: String = ""
)

@Entity(
    tableName = "libros_generos",
    foreignKeys = [
        ForeignKey(
            entity = Genero::class,
            parentColumns = ["generoId"],
            childColumns = ["generoOwnerId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class LibroGenero(
    @PrimaryKey val libroId: String,
    @ColumnInfo(index = true) val generoOwnerId: Int,
    val titulo: String,
    val autor: String
)

data class GeneroConLibros(
    @Embedded val genero: Genero,
    @Relation(
        parentColumn = "generoId",
        entityColumn = "generoOwnerId"
    )
    val libros: List<LibroGenero>
)