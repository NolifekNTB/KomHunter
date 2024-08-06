package com.example.komhunter.uploadGPX.ui

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import com.example.komhunter.uploadGPX.model.parseGpx
import kotlinx.coroutines.launch
import java.io.InputStream

@Composable
fun GpxFilePicker(
    viewModel: GpxViewModel,
    onNavigate: () -> Unit
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()


    val gpxFileLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ) { uri: Uri? ->
        uri?.let {
            coroutineScope.launch {
                context.contentResolver.takePersistableUriPermission(
                    it, Intent.FLAG_GRANT_READ_URI_PERMISSION
                )

                val inputStream: InputStream? = context.contentResolver.openInputStream(it)
                inputStream?.let { stream ->
                    val coordinates = parseGpx(stream)

                    viewModel.insertAll(coordinates)

                    onNavigate()
                }
            }
        }
    }

    Button(onClick = { gpxFileLauncher.launch(arrayOf("application/gpx+xml", "application/octet-stream")) }) {
        Text("Select GPX File")
    }
}