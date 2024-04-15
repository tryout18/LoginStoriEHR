package com.edgarhrdevs.loginstoriehr.framework

import android.content.ContentValues
import android.content.Context
import android.os.Build
import android.provider.MediaStore
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.edgarhrdevs.loginstoriehr.data.datasources.CameraDataSource
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

class CameraAndroidDataSource @Inject constructor(
    private val cameraProvider: ProcessCameraProvider,
    private val selector: CameraSelector,
    private val preview: Preview,
    private val imageAnalysis: ImageAnalysis,
    private val imageCapture: ImageCapture
) : CameraDataSource {


    override suspend fun captureAndSaveImage(
        context: Context
    ) {

        val name = SimpleDateFormat(
            "yyyy-MM-dd-HH-mm-ss-SSS",
            Locale.ENGLISH
        ).format(System.currentTimeMillis())


        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME,name)
            put(MediaStore.MediaColumns.MIME_TYPE,"image/jpeg")
            if (Build.VERSION.SDK_INT > 28){
                put(MediaStore.Images.Media.RELATIVE_PATH,"Pictures/My-Camera-App-Images")
            }
        }

        val outputOptions = ImageCapture.OutputFileOptions
            .Builder(
                context.contentResolver,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                contentValues
            )
            .build()

        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(context),
            object : ImageCapture.OnImageSavedCallback{
                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    Toast.makeText(
                        context,
                        "Saved image ${outputFileResults.savedUri!!}",
                        Toast.LENGTH_LONG
                    ).show()
                }

                override fun onError(exception: ImageCaptureException) {
                    Toast.makeText(
                        context,
                        "some error occurred ${exception.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        )

    }




    override suspend fun showCameraPreview(
        previewView: PreviewView,
        lifecycleOwner: LifecycleOwner
    ) {

        preview.setSurfaceProvider(previewView.surfaceProvider)
        try {
            cameraProvider.unbindAll()
            cameraProvider.bindToLifecycle(
                lifecycleOwner,
                selector,
                preview,
                imageAnalysis,
                imageCapture
            )
        }catch (e:Exception){
            e.printStackTrace()
        }
    }

}