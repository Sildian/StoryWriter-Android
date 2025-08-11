package com.sildian.apps.storywriter.domainlayer.scene

interface SceneRepository {
    suspend fun getAllScenes(): Result<List<Scene>>
    suspend fun saveScene(scene: Scene): Result<Long>
}