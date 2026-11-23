package edu.marhuendasamuel.appdummy

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import edu.marhuendasamuel.appdummy.data.datasource.local.AppDatabase
import edu.marhuendasamuel.appdummy.data.datasource.local.LibrosDao
import org.junit.After
import org.junit.Before
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

    //TODO: porogramar los tests
}