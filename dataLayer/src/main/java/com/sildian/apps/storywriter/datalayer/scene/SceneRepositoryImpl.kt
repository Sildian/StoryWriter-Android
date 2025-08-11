package com.sildian.apps.storywriter.datalayer.scene

import com.sildian.apps.storywriter.datalayer.StoryWriterDatabase
import com.sildian.apps.storywriter.domainlayer.scene.Scene
import com.sildian.apps.storywriter.domainlayer.scene.SceneRepository

internal class SceneRepositoryImpl(
    private val storyWriterDatabase: StoryWriterDatabase,
) : SceneRepository {

    override suspend fun getAllScenes(): Result<List<Scene>> = runCatching {
        storyWriterDatabase.sceneDao().getAll().map { it.toDomain() }
    }

    override suspend fun saveScene(scene: Scene): Result<Long> = runCatching {
        if (scene.id == 0L) {
            storyWriterDatabase.sceneDao().insert(scene = scene.toDb())
        } else {
            storyWriterDatabase.sceneDao().update(scene = scene.toDb()).let { scene.id }
        }
    }
}