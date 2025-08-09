package com.sildian.apps.storywriter.domainlayer.scene

fun interface SaveSceneUseCase {
    suspend operator fun invoke(scene: Scene): Result<Long>
}

internal class SaveSceneUseCaseImpl(
    private val sceneRepository: SceneRepository,
) : SaveSceneUseCase {

    override suspend operator fun invoke(scene: Scene): Result<Long> =
        sceneRepository.saveScene(scene = scene)
}