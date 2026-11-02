package edu.marhuendasamuel.appdummy.screens.detalle

import edu.marhuendasamuel.appdummy.data.model.Libro

sealed class DetalleUiState {
    data object Cargando : DetalleUiState()
    data class Exito(val libro: Libro) : DetalleUiState()
    data object NoEncontrado: DetalleUiState()
}