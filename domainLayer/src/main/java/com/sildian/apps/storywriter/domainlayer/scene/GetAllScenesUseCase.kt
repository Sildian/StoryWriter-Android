package com.sildian.apps.storywriter.domainlayer.scene

fun interface GetAllScenesUseCase {
    suspend operator fun invoke(): Result<List<Scene>>
}

internal class GetAllScenesUseCaseImpl(
    private val sceneRepository: SceneRepository,
) : GetAllScenesUseCase {

    override suspend operator fun invoke(): Result<List<Scene>> =
        sceneRepository.getAllScenes()
}