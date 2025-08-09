package com.sildian.apps.storywriter.datalayer.scene

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
internal data class SceneDb(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val description: String,
)
