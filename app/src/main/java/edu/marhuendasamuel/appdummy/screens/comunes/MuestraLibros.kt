package edu.marhuendasamuel.appdummy.screens.common

import android.util.Patterns
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import edu.marhuendasamuel.appdummy.R
import edu.marhuendasamuel.appdummy.data.model.Libro
import edu.marhuendasamuel.appdummy.screens.listado.LibrosUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MuestraLibros(
    tituloApp: String = "AppDummy",
    busqueda: String,
    onBusquedaChanged: (String) -> Unit,
    autores: List<String>,
    autorSeleccionado: String,
    onAutorSelected: (String) -> Unit,
    uiState: LibrosUiState,
    onNavegaADetalle: (Int) -> Unit,
    onToggleLeido: (Libro) -> Unit,
    onToggleFavorito: (Libro) -> Unit,
    onReintentar: () -> Unit,
    onPerfilClick: () -> Unit = {},
    snackbarHostState: SnackbarHostState
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(tituloApp) },
                actions = {
                    IconButton(onClick = onPerfilClick) {
                        Icon(Icons.Default.AccountCircle, contentDescription = "Perfil")
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            // Barra de búsqueda
            OutlinedTextField(
                value = busqueda,
                onValueChange = onBusquedaChanged,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                placeholder = { Text("Buscar libros...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                trailingIcon = {
                    AnimatedVisibility(visible = busqueda.isNotEmpty()) {
                        IconButton(onClick = { onBusquedaChanged("") }) {
                            Icon(Icons.Default.Clear, contentDescription = "Borrar búsqueda")
                        }
                    }
                },
                singleLine = true
            )

            // Chips de autores
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(bottom = 8.dp)
            ) {
                items(autores) { autor ->
                    FilterChip(
                        selected = autor == autorSeleccionado,
                        onClick = { onAutorSelected(autor) },
                        label = { Text(autor) }
                    )
                }
            }

            // Estados de la UI
            when (val estado = uiState) {
                is LibrosUiState.Cargando -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }

                is LibrosUiState.Exito -> {
                    val librosFiltrados = estado.libros.filter {
                        val coincideBusqueda = busqueda.isBlank() || it.titulo.contains(busqueda, ignoreCase = true)
                        val coincideGenero = autorSeleccionado == "Todos" || it.autor == autorSeleccionado
                        coincideBusqueda && coincideGenero
                    }

                    if (librosFiltrados.isEmpty()) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Icon(
                                    Icons.Default.SearchOff,
                                    contentDescription = null,
                                    modifier = Modifier.size(64.dp),
                                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                                Text(
                                    "Sin resultados para \"$busqueda\"",
                                    style = MaterialTheme.typography.bodyLarge
                                )
                            }
                        }
                    } else {
                        LazyVerticalGrid(
                            contentPadding = PaddingValues(horizontal = 8.dp),
                            columns = GridCells.Fixed(2),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(librosFiltrados, key = { it.id }) { libro ->
                                ItemLibro(
                                    libro = libro,
                                    onClickItem = { onNavegaADetalle(libro.id) },
                                    onToggleLeido = { onToggleLeido(libro) },
                                    onToggleFavorito = { onToggleFavorito(libro) }
                                )
                            }
                        }
                    }
                }

                is LibrosUiState.Error -> {
                    Column(
                        modifier = Modifier.fillMaxSize().padding(32.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            Icons.Default.ErrorOutline,
                            contentDescription = null,
                            modifier = Modifier.size(64.dp),
                            tint = MaterialTheme.colorScheme.error
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = estado.mensaje,
                            color = MaterialTheme.colorScheme.error
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = onReintentar) { Text("Reintentar") }
                    }
                }
            }
        }
    }
}

@Composable
fun ItemLibro(
    libro: Libro,
    onClickItem: () -> Unit = {},
    onToggleLeido: (Int) -> Unit = {},
    onToggleFavorito: (Int) -> Unit = {}
) {
    Card(onClick = onClickItem, modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(8.dp)
        ) {
            Text(libro.titulo, style = MaterialTheme.typography.titleSmall)
            if (Patterns.WEB_URL.matcher(libro.cover).matches())
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(libro.cover)
                        .crossfade(true)
                        .build(),
                    contentDescription = "Portada de ${libro.titulo}",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.width(200.dp)
                )
            else // Si no es una URL válida, se muestra una imagen por defecto
                AsyncImage(
                    model = R.drawable.nocover,
                    contentDescription = "Portada de ${libro.titulo}",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.width(200.dp)
                )

            Text(
                text = "${libro.autor} • ${libro.year}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                IconButton(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(0.5f),
                    onClick = { onToggleLeido(libro.id) }) {
                    Icon(
                        imageVector = if (libro.leido) Icons.Default.BookmarkAdded
                        else Icons.Default.BookmarkBorder,
                        contentDescription = if (libro.leido) "Quitar leído" else "Marcar como leído",
                        tint = if (libro.leido) MaterialTheme.colorScheme.error
                        else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                IconButton(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(0.5f),
                    onClick = { onToggleFavorito(libro.id) }) {
                    Icon(
                        imageVector = if (libro.esFavorito) Icons.Default.Favorite
                        else Icons.Default.FavoriteBorder,
                        contentDescription = if (libro.esFavorito) "Quitar favorito" else "Añadir favorito",
                        tint = if (libro.esFavorito) MaterialTheme.colorScheme.error
                        else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}