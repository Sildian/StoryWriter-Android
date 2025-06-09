package com.sildian.apps.storywriter.datalayer.scene

import com.sildian.apps.storywriter.datalayer.StoryWriterDatabase
import com.sildian.apps.storywriter.domainlayer.scene.Scene
import com.sildian.apps.storywriter.domainlayer.scene.SceneRepository

internal class SceneRepositoryImpl(
    private val storyWriterDatabase: StoryWriterDatabase,
) : SceneRepository {

    override suspend fun saveScene(scene: Scene): Result<Unit> = runCatching {
        if (scene.id == 0) {
            storyWriterDatabase.sceneDao().insert(scene = scene.toDb())
        } else {
            storyWriterDatabase.sceneDao().update(scene = scene.toDb())
        }
    }
}