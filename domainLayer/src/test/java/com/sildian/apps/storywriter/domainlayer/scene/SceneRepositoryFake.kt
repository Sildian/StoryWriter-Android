package com.sildian.apps.storywriter.domainlayer.scene

open class SceneRepositoryFake : SceneRepository {
    override suspend fun addScene(scene: Scene): Result<Unit> = Result.success(Unit)
    override suspend fun updateScene(scene: Scene): Result<Unit> = Result.success(Unit)
}