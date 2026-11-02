package edu.marhuendasamuel.appdummy.navegacion

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Book
import androidx.compose.ui.graphics.vector.ImageVector

data class ItemNavegacion<T : Any>(
    val etiqueta: String,
    val ruta: T,
    val icono: ImageVector,
    val iconoSeleccionado: ImageVector = icono
)

val ItemsNavegacionPrincipal = listOf(
    ItemNavegacion(
        etiqueta = "Catálogo",
        ruta = Listado,
        icono = Icons.Outlined.Book,
        iconoSeleccionado = Icons.Filled.Book
    ),
    ItemNavegacion(
        etiqueta = "Favoritos",
        ruta = Favoritos,
        icono = Icons.Default.FavoriteBorder,
        iconoSeleccionado = Icons.Filled.Favorite
    )
)