package com.sildian.apps.storywriter.domainlayer.scene

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlin.random.Random

open class SceneRepositoryFake : SceneRepository {

    override fun getAllScenes(): Flow<List<Scene>> =
        flowOf(
            List(size = Random.nextInt(from = 1, until = 10)) { index ->
                Random.nextScene(id = index.toLong())
            }
        )

    override suspend fun saveScene(scene: Scene): Result<Long> =
        Result.success(Random.nextLong(from = 1, until = 100))
}