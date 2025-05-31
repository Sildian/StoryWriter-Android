package com.sildian.apps.storywriter.datalayer.scene

import com.sildian.apps.storywriter.datalayer.StoryWriterDatabase
import com.sildian.apps.storywriter.domainlayer.scene.Scene
import com.sildian.apps.storywriter.domainlayer.scene.SceneRepository

internal class SceneRepositoryImpl(
    private val storyWriterDatabase: StoryWriterDatabase,
) : SceneRepository {

    override suspend fun addScene(scene: Scene): Result<Unit> = runCatching {
        storyWriterDatabase.sceneDao().insert(scene = scene.toDb())
    }

    override suspend fun updateScene(scene: Scene): Result<Unit> = runCatching {
        storyWriterDatabase.sceneDao().update(scene = scene.toDb())
    }
}