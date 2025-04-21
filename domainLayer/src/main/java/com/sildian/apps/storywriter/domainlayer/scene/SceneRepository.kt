package com.sildian.apps.storywriter.domainlayer.scene

interface SceneRepository {
    suspend fun addScene(scene: Scene): Result<Unit>
    suspend fun updateScene(scene: Scene): Result<Unit>
}