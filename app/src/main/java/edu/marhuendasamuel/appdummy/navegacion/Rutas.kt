package edu.marhuendasamuel.appdummy.navegacion

import kotlinx.serialization.Serializable

@Serializable data object Inicio
@Serializable data object Listado
@Serializable data class Detalle(val id: Int)
@Serializable data object Favoritos