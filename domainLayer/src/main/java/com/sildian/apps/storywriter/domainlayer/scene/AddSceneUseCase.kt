package com.sildian.apps.storywriter.domainlayer.scene

fun interface AddSceneUseCase {
    suspend operator fun invoke(scene: Scene): Result<Unit>
}

internal class AddSceneUseCaseImpl(
    private val sceneRepository: SceneRepository,
) : AddSceneUseCase {

    override suspend operator fun invoke(scene: Scene): Result<Unit> =
        sceneRepository.addScene(scene = scene)
}