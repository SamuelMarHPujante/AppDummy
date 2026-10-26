package edu.marhuendasamuel.appdummy.screens

import androidx.annotation.experimental.Experimental
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import android.Manifest
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts

sealed class EstadoPermiso {
    data object Concedido : EstadoPermiso()
    data object Pendiente : EstadoPermiso()
    data object Denegado : EstadoPermiso()
    data object DenegadoPermanentemente : EstadoPermiso()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaGestionPermisos() {
    val context = LocalContext.current

    val estadoPermiso by remember {
        val concedido = ContextCompat.checkSelfPermission(
            context, Manifest.permission.CAMERA
        ) == android.content.pm.PackageManager.PERMISSION_GRANTED

        mutableStateOf(if (concedido) EstadoPermiso.Concedido else EstadoPermiso.Pendiente)
    }
}