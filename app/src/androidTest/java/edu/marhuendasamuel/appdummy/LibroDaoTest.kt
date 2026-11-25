package edu.marhuendasamuel.appdummy

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import edu.marhuendasamuel.appdummy.data.datasource.local.AppDatabase
import edu.marhuendasamuel.appdummy.data.datasource.local.LibrosDao
import edu.marhuendasamuel.appdummy.data.model.Libro
import junit.framework.TestCase.assertTrue
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LibroDaoTest {
    private lateinit var database: AppDatabase
    private lateinit var dao: LibrosDao

    @Before
    fun crearBaseDeDatos() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        )
            .allowMainThreadQueries()
            .build()
        dao = database.libroDao()
    }

    @After
    fun cerrarBaseDeDatos() { database.close() }

    @Test
    fun insertarYRecuperarLibro() = runTest {
        val libro = Libro(
            id = "2",
            titulo = "Juego de tronos",
            autor = "George R.R. Martin",
            year = 1996,
            isbn = "9780307951182",
            cover = "https://covers.openlibrary.org/b/isbn/9780307951182-L.jpg"
        )
        dao.upsert(libro)

        dao.toggleFavorito("2")
        assertTrue(dao.obtenerPorId("2")!!.esFavorito)

        dao.toggleFavorito("2")
        assertFalse(dao.obtenerPorId("2")!!.esFavorito)
    }

    @Test
    fun upsertConservandoFavoritoNoSobreescribeFavorito() = runTest {
        val libroOriginal = Libro(
            "3",
            "Festín de cuervos",
            "George R.R. Martin",
            2005,
            "9780307951212",
            "https://covers.openlibrary.org/b/isbn/9780307951212-L.jpg",
            true,
            false
        )
        dao.upsert(libroOriginal)

        val libroDeApi = libroOriginal.copy(
            titulo = "Festín de cisnes",
            year = 2026,
            esFavorito = false
        )
        dao.upsertConservandoFavorito(listOf(libroDeApi))

        val resultado = dao.obtenerPorId("3")
        assertEquals("Festín de cisnes", resultado?.titulo)
        assertEquals(2026, resultado?.year)
        assertTrue(resultado!!.esFavorito)
    }

    @Test
    fun observarFavoritasEmiteSoloLasMarcadas() = runTest {
        dao.upsert(
            Libro(
                id = "1",
                titulo = "Proyecto Hail Mary",
                autor = "Andy Weir",
                year = 2021,
                isbn = "9788418037016",
                cover = "https://covers.openlibrary.org/b/isbn/9788418037016-L.jpg",
                esFavorito = true,
                leido = false
            )
        )
        dao.upsert(
            Libro(
                id = "2",
                titulo = "Juego de tronos",
                autor = "George R.R. Martin",
                year = 1996,
                isbn = "9780307951182",
                cover = "https://covers.openlibrary.org/b/isbn/9780307951182-L.jpg",
                esFavorito = true,
                leido = true
            )
        )
        dao.upsert(
            Libro(
                "3",
                "Festín de cuervos",
                "George R.R. Martin",
                2005,
                "9780307951212",
                "https://covers.openlibrary.org/b/isbn/9780307951212-L.jpg",
                false,
                false
            ),
        )

        val favoritas = dao.observarFavoritos().first()
        assertEquals(2, favoritas.size)
        assertTrue(favoritas.all { it.esFavorito })
    }
}