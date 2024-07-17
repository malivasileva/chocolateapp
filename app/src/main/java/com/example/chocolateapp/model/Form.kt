package com.example.chocolateapp.model

import android.hardware.camera2.CameraExtensionSession.StillCaptureLatency
import androidx.annotation.DrawableRes
import com.example.chocolateapp.R

data class Form(
    val title: String,
    val weight: Int,
    @DrawableRes val imageId: Int = R.drawable.default_chocolate
)
