package com.sildian.apps.storywriter.datalayer.scene

import com.sildian.apps.storywriter.datalayer.StoryWriterDatabase
import com.sildian.apps.storywriter.domainlayer.scene.Scene
import com.sildian.apps.storywriter.domainlayer.scene.SceneRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class SceneRepositoryImpl(
    private val storyWriterDatabase: StoryWriterDatabase,
) : SceneRepository {

    override fun getAllScenes(): Flow<List<Scene>> =
        storyWriterDatabase.sceneDao().getAll().map { scenes -> scenes.map { it.toDomain() } }

    override suspend fun saveScene(scene: Scene): Result<Long> = runCatching {
        if (scene.id == 0L) {
            storyWriterDatabase.sceneDao().insert(scene = scene.toDb())
        } else {
            storyWriterDatabase.sceneDao().update(scene = scene.toDb()).let { scene.id }
        }
    }
}