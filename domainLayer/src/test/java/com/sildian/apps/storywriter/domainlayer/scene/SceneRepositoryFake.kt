package com.sildian.apps.storywriter.domainlayer.scene

open class SceneRepositoryFake : SceneRepository {
    override suspend fun saveScene(scene: Scene): Result<Unit> = Result.success(Unit)
}