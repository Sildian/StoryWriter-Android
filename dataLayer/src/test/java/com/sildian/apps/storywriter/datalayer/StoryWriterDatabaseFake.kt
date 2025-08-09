package com.sildian.apps.storywriter.datalayer

import androidx.room.InvalidationTracker
import com.sildian.apps.storywriter.datalayer.scene.SceneDao
import com.sildian.apps.storywriter.datalayer.scene.SceneDaoFake

internal open class StoryWriterDatabaseFake : StoryWriterDatabase() {

    override fun sceneDao(): SceneDao = SceneDaoFake()

    override fun clearAllTables() {
        TODO("Not yet implemented")
    }

    override fun createInvalidationTracker(): InvalidationTracker {
        TODO("Not yet implemented")
    }
}