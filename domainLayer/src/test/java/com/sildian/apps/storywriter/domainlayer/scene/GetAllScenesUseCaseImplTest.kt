package com.sildian.apps.storywriter.domainlayer.scene

import kotlinx.coroutines.test.runTest
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertEquals

class GetAllScenesUseCaseImplTest {

    @Test
    fun `useCase should fail when repository fails`() = runTest {
        // Given
        val exception = Throwable()
        val repository = object : SceneRepositoryFake() {
            override suspend fun getAllScenes(): Result<List<Scene>> =
                Result.failure(exception = exception)
        }
        val useCase = initUseCase(sceneRepository = repository)

        // When
        val result = useCase()

        // Then
        assertEquals(expected = exception, actual = result.exceptionOrNull())
    }

    @Test
    fun `useCase should return all scenes when repository succeeds`() = runTest {
        // Given
        val scenes = List(size = 3) { Random.nextScene() }
        val repository = object : SceneRepositoryFake() {
            override suspend fun getAllScenes(): Result<List<Scene>> =
                Result.success(scenes)
        }
        val useCase = initUseCase(sceneRepository = repository)

        // When
        val result = useCase()

        // Then
        assertEquals(expected = scenes, actual = result.getOrNull())
    }

    private fun initUseCase(
        sceneRepository: SceneRepository = SceneRepositoryFake(),
    ): GetAllScenesUseCase =
        GetAllScenesUseCaseImpl(sceneRepository = sceneRepository)
}