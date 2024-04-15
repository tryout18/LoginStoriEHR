package com.edgarhrdevs.loginstoriehr.data.datasources

import android.content.Context
import androidx.camera.view.PreviewView
import androidx.lifecycle.LifecycleOwner

interface CameraDataSource {

    suspend fun captureAndSaveImage(context: Context)
    suspend fun showCameraPreview(
        previewView: PreviewView,
        lifecycleOwner: LifecycleOwner
    )
}