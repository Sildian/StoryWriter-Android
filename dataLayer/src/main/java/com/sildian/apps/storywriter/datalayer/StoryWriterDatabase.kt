package com.sildian.apps.storywriter.datalayer

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sildian.apps.storywriter.datalayer.scene.SceneDao
import com.sildian.apps.storywriter.datalayer.scene.SceneDb

@Database(entities = [SceneDb::class], version = 1)
internal abstract class StoryWriterDatabase : RoomDatabase() {
    abstract fun sceneDao(): SceneDao
}