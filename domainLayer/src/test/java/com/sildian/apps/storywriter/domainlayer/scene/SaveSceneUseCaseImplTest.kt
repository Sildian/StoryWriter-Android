package com.sildian.apps.storywriter.domainlayer.scene

import kotlinx.coroutines.test.runTest
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class SaveSceneUseCaseImplTest {

    @Test
    fun `GIVEN failure from repository WHEN invoke THEN return failure`() = runTest {
        // Given
        val exception = Throwable()
        val repository = object : SceneRepositoryFake() {
            override suspend fun saveScene(scene: Scene): Result<Long> =
                Result.failure(exception = exception)
        }
        val useCase = initUseCase(sceneRepository = repository)

        // When
        val result = useCase(scene = Random.nextScene())

        // Then
        assertEquals(expected = exception, actual = result.exceptionOrNull())
    }

    @Test
    fun `GIVEN success from repository WHEN invoke THEN return success`() = runTest {
        // Given
        val repository = object : SceneRepositoryFake() {
            override suspend fun saveScene(scene: Scene): Result<Long> =
                Result.success(Random.nextLong(from = 1, until = 100))
        }
        val useCase = initUseCase(sceneRepository = repository)

        // When
        val result = useCase(scene = Random.nextScene())

        // Then
        assertTrue(result.isSuccess)
    }

    private fun initUseCase(
        sceneRepository: SceneRepository = SceneRepositoryFake(),
    ): SaveSceneUseCase =
        SaveSceneUseCaseImpl(sceneRepository = sceneRepository)
}