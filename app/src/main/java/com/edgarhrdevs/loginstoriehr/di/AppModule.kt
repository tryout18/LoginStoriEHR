package com.edgarhrdevs.loginstoriehr.di

import android.app.Application
import androidx.camera.core.AspectRatio
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageCapture
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import com.edgarhrdevs.loginstoriehr.data.datasources.CameraDataSource
import com.edgarhrdevs.loginstoriehr.data.datasources.FirebaseAuthDataSource
import com.edgarhrdevs.loginstoriehr.data.datasources.FirebaseFirestoreDataSource
import com.edgarhrdevs.loginstoriehr.framework.CameraAndroidDataSource
import com.edgarhrdevs.loginstoriehr.framework.FireBaseAndroidAuthDataSource
import com.edgarhrdevs.loginstoriehr.framework.FirebaseAndroidFirestoreDataSource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import kotlin.random.Random

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideCameraSelector(): CameraSelector {
        return CameraSelector.Builder()
            .requireLensFacing(CameraSelector.LENS_FACING_BACK)
            .build()
    }

    @Provides
    @Singleton
    fun provideCameraProvider(application: Application)
            : ProcessCameraProvider {
        return ProcessCameraProvider.getInstance(application).get()

    }

    @Provides
    @Singleton
    fun provideCameraPreview(): Preview {
        return Preview.Builder().build()
    }

    @Provides
    @Singleton
    fun provideImageCapture(): ImageCapture {
        return ImageCapture.Builder()
            .setFlashMode(ImageCapture.FLASH_MODE_ON)
            .setTargetAspectRatio(AspectRatio.RATIO_16_9)
            .build()
    }

    @Provides
    @Singleton
    fun provideImageAnalysis(): ImageAnalysis {
        return ImageAnalysis.Builder()
            .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
            .build()
    }

    @Provides
    @Singleton
    fun provideCameraAndroidDataSource(
        cameraProvider: ProcessCameraProvider,
        selector: CameraSelector,
        imageCapture: ImageCapture,
        imageAnalysis: ImageAnalysis,
        preview: Preview
    ): CameraDataSource {
        return CameraAndroidDataSource(
            cameraProvider,
            selector,
            preview,
            imageAnalysis,
            imageCapture
        )
    }

    @Provides
    @Singleton
    fun provideRandomNumber(): Random = Random

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideFirebaseFireStore(): FirebaseFirestore = FirebaseFirestore.getInstance()

    @Provides
    @Singleton
    fun provideFireBaseAndroidAuthDataSource(repository: FireBaseAndroidAuthDataSource): FirebaseAuthDataSource =
        repository

    @Provides
    @Singleton
    fun provideFireBaseAndroidFireStoreDataSource(repository: FirebaseAndroidFirestoreDataSource): FirebaseFirestoreDataSource =
        repository
}

@Module
@InstallIn(SingletonComponent::class)
abstract class AppDataModule {
    @Binds
    abstract fun provideFireBaseAuthDatasource(firebaseAuthDataSource: FirebaseAuthDataSource): FirebaseAuthDataSource

    @Binds
    abstract fun provideFirebaseFireStoreDataSource(firebaseFireStoreDataSource: FirebaseFirestoreDataSource): FirebaseFirestoreDataSource

}