package com.example.findsomethingtodo.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RandomActivity(
    val accessibility: Double,
    val activity: String,
    val key: String,
    val link: String,
    val participants: Int,
    val price: Double,
    val type: String
):Parcelable