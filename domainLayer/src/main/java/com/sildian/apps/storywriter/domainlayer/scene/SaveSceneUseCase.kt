package com.sildian.apps.storywriter.domainlayer.scene

fun interface SaveSceneUseCase {
    suspend operator fun invoke(scene: Scene): Result<Unit>
}

internal class SaveSceneUseCaseImpl(
    private val sceneRepository: SceneRepository,
) : SaveSceneUseCase {

    override suspend operator fun invoke(scene: Scene): Result<Unit> =
        sceneRepository.saveScene(scene = scene)
}