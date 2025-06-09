package com.sildian.apps.storywriter.domainlayer.scene

interface SceneRepository {
    suspend fun saveScene(scene: Scene): Result<Unit>
}