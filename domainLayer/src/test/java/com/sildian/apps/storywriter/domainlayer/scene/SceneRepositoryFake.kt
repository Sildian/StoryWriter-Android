package com.sildian.apps.storywriter.domainlayer.scene

import kotlin.random.Random

open class SceneRepositoryFake : SceneRepository {
    override suspend fun saveScene(scene: Scene): Result<Long> =
        Result.success(Random.nextLong(from = 1, until = 100))
}