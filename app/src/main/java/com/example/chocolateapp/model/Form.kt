package com.example.chocolateapp.model

import android.hardware.camera2.CameraExtensionSession.StillCaptureLatency
import androidx.annotation.DrawableRes

data class Form(
    val title: String,
    val weight: Int,
    @DrawableRes val imageId: Int
)
