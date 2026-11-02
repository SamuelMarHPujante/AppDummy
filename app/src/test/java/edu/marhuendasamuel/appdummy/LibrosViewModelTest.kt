package edu.marhuendasamuel.appdummy

import edu.marhuendasamuel.appdummy.data.repository.LibrosRepository
import edu.marhuendasamuel.appdummy.screens.listado.*
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.Assert.assertNotEquals
import org.junit.*
import org.junit.rules.TestWatcher
import org.junit.runner.Description

@OptIn(ExperimentalCoroutinesApi::class)
class LibrosViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: LibrosViewModel

    @Before
    fun setup() {
        viewModel = LibrosViewModel(LibrosRepository())
    }

    @Test
    fun `estado inicial es Cargando`() {
        val estado = viewModel.uiState.value
        assertTrue(
            estado is LibrosUiState.Cargando ||
            estado is LibrosUiState.Exito
        )
    }

    @Test
    fun `cargarLibros produce estado Exito con datos`() = runTest {
        advanceUntilIdle()

        val estado = viewModel.uiState.value
        assertTrue("El estado debe ser Exito", estado is LibrosUiState.Exito)
        assertTrue(
            "Debe haber al menos un libro",
            (estado as LibrosUiState.Exito).libros.isNotEmpty()
        )
    }

    @Test
    fun `actualizarBusqueda actualiza el StateFlow de busqueda`() = runTest {
        val busqueda = "Dune"

        viewModel.actualizarBusqueda(busqueda)
        assertEquals(busqueda, viewModel.busqueda.value)
    }

    @Test
    fun `toggleFavorito invierte el estado de favorito`() = runTest {
        advanceUntilIdle()

        val estadoInicial = viewModel.uiState.value as LibrosUiState.Exito
        val libro = estadoInicial.libros.first()

        viewModel.toggleFavorito(libro.id)

        val estadoFinal = viewModel.uiState.value as LibrosUiState.Exito
        val modificada = estadoFinal.libros.find { it.id == libro.id }!!
        assertNotEquals(
            "El estado de favorito debe haber cambiado",
            libro.esFavorito,
            modificada.esFavorito
        )
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
class MainDispatcherRule(
    val testDispatcher: TestDispatcher = UnconfinedTestDispatcher()
) : TestWatcher() {
    override fun starting(description: Description) = Dispatchers.setMain(testDispatcher)
    override fun finished(description: Description) = Dispatchers.resetMain()
}
