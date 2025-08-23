package com.sildian.apps.storywriter.domainlayer.scene

import app.cash.turbine.test
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
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
            override fun getAllScenes(): Flow<List<Scene>> = flow { throw exception }
        }
        val useCase = initUseCase(sceneRepository = repository)

        // When
        useCase().test {

            // Then
            assertEquals(expected = exception, actual = awaitError())
        }
    }

    @Test
    fun `useCase should return all scenes when repository succeeds`() = runTest {
        // Given
        val scenes = List(size = 3) { Random.nextScene() }
        val repository = object : SceneRepositoryFake() {
            override fun getAllScenes(): Flow<List<Scene>> = flowOf(scenes)
        }
        val useCase = initUseCase(sceneRepository = repository)

        // When
        useCase().test {

            // Then
            assertEquals(expected = scenes, actual = awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    private fun initUseCase(
        sceneRepository: SceneRepository = SceneRepositoryFake(),
    ): GetAllScenesUseCase =
        GetAllScenesUseCaseImpl(sceneRepository = sceneRepository)
}