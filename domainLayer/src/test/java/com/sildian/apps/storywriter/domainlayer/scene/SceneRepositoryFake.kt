package com.sildian.apps.storywriter.domainlayer.scene

import kotlin.random.Random

open class SceneRepositoryFake : SceneRepository {

    override suspend fun getAllScenes(): Result<List<Scene>> =
        Result.success(
            List(size = Random.nextInt(from = 1, until = 10)) { index ->
                Random.nextScene(id = index.toLong())
            }
        )

    override suspend fun saveScene(scene: Scene): Result<Long> =
        Result.success(Random.nextLong(from = 1, until = 100))
}