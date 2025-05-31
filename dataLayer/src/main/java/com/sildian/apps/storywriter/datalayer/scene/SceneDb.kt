package com.sildian.apps.storywriter.datalayer.scene

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
internal data class SceneDb(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val description: String,
)
