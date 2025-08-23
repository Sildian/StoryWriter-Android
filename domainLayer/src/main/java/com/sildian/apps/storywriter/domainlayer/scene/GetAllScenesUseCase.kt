package com.sildian.apps.storywriter.domainlayer.scene

import kotlinx.coroutines.flow.Flow

fun interface GetAllScenesUseCase {
    operator fun invoke(): Flow<List<Scene>>
}

internal class GetAllScenesUseCaseImpl(
    private val sceneRepository: SceneRepository,
) : GetAllScenesUseCase {

    override operator fun invoke(): Flow<List<Scene>> =
        sceneRepository.getAllScenes()
}