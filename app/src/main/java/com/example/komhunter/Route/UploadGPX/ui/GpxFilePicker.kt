package com.example.komhunter.Route.UploadGPX.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import com.example.komhunter.Route.UploadGPX.domain.parseGpx
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import java.io.InputStream

@Composable
fun GpxFilePicker(viewModel: GpxViewModel = koinViewModel(), onNavigate: () -> Unit) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val gpxFileLauncher = rememberGpxFileLauncher(context, coroutineScope, viewModel, onNavigate)

    FilePickerUI(gpxFileLauncher)
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun FilePickerUI(gpxFileLauncher: ManagedActivityResultLauncher<Array<String>, Uri?>) {
    Column(
        modifier = Modifier.fillMaxSize().semantics {
            testTagsAsResourceId = true
        },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(
            modifier = Modifier.testTag("GpxFilePicker"),
            onClick = {
                gpxFileLauncher.launch(arrayOf("application/gpx+xml", "application/octet-stream"))
            }
        ) {
            Text("Select GPX File")
        }
    }
}

@Composable
fun rememberGpxFileLauncher(
    context: Context,
    coroutineScope: CoroutineScope,
    viewModel: GpxViewModel,
    onNavigate: () -> Unit
): ManagedActivityResultLauncher<Array<String>, Uri?> {
    return rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ) { uri: Uri? ->
        uri?.let {
            coroutineScope.launch {
                handleGpxFileUri(context, it, viewModel, onNavigate)
            }
        }
    }
}

suspend fun handleGpxFileUri(
    context: Context,
    uri: Uri,
    viewModel: GpxViewModel,
    onNavigate: () -> Unit
) {
    try {
        context.contentResolver.takePersistableUriPermission(uri, Intent.FLAG_GRANT_READ_URI_PERMISSION)
    } catch (e: SecurityException) {
        println("Permission denied: $e")
    }

    context.contentResolver.openInputStream(uri)?.use { stream ->
        val coordinates = parseGpx(stream)
        if (coordinates.isNotEmpty()) {
            viewModel.deleteAll()
            viewModel.insertAll(coordinates) {
                onNavigate()
            }
        }
    }

}
