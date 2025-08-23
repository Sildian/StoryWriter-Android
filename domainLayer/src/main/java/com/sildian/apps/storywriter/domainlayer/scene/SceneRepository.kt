package com.sildian.apps.storywriter.domainlayer.scene

import kotlinx.coroutines.flow.Flow

interface SceneRepository {
    fun getAllScenes(): Flow<List<Scene>>
    suspend fun saveScene(scene: Scene): Result<Long>
}