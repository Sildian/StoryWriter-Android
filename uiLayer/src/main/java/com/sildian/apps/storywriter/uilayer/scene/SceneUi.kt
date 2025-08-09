package com.sildian.apps.storywriter.uilayer.scene

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SceneUi(
    val id: Long = 0,
    val description: String = "",
) : Parcelable
