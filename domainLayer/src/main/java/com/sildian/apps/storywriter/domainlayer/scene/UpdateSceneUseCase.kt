package com.sildian.apps.storywriter.domainlayer.scene

fun interface UpdateSceneUseCase {
    suspend operator fun invoke(scene: Scene): Result<Unit>
}

internal class UpdateSceneUseCaseImpl(
    private val sceneRepository: SceneRepository,
) : UpdateSceneUseCase {

    override suspend operator fun invoke(scene: Scene): Result<Unit> =
        sceneRepository.updateScene(scene = scene)
}