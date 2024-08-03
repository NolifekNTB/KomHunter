package com.example.komhunter.uploadGPX.ui

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.platform.LocalContext
import com.example.komhunter.core.database.GpxCoordinate
import com.example.komhunter.core.database.GpxDatabase
import com.example.komhunter.uploadGPX.model.parseGpx
import kotlinx.coroutines.launch
import java.io.InputStream

@Composable
fun GpxFilePicker(
    onGpxParsed: (List<GpxCoordinate>) -> Unit
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val onGpxParsedState = rememberUpdatedState(onGpxParsed)


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

                    val db = GpxDatabase.getDatabase(context)
                    db.gpxCoordinateDao().insertAll(coordinates)

                    onGpxParsedState.value(coordinates)
                }
            }
        }
    }

    Button(onClick = { gpxFileLauncher.launch(arrayOf("application/gpx+xml", "application/octet-stream")) }) {
        Text("Select GPX File")
    }
}