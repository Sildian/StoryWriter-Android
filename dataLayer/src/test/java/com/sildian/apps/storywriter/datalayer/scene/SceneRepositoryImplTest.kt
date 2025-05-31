package com.sildian.apps.storywriter.datalayer.scene

import com.sildian.apps.storywriter.datalayer.StoryWriterDatabase
import com.sildian.apps.storywriter.datalayer.StoryWriterDatabaseFake
import com.sildian.apps.storywriter.domainlayer.scene.SceneRepository
import com.sildian.apps.storywriter.domainlayer.scene.nextScene
import kotlinx.coroutines.test.runTest
import java.io.IOException
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertEquals

class SceneRepositoryImplTest {

    @Test
    fun `GIVEN failure WHEN addScene THEN result is failure`() = runTest {
        // Given
        val error = IOException()
        val repository = initRepository(
            storyWriterDatabase = object : StoryWriterDatabaseFake() {
                override fun sceneDao(): SceneDao = object : SceneDaoFake() {
                    override suspend fun insert(scene: SceneDb) = throw error
                }
            }
        )

        // When
        val result = repository.addScene(scene = Random.nextScene())

        // Then
        assertEquals(expected = Result.failure(error), actual = result)
    }

    @Test
    fun `GIVEN success WHEN addScene THEN result is success`() = runTest {
        // Given
        val scene = Random.nextScene()
        val repository = initRepository()

        // When
        val result = repository.addScene(scene = scene)

        // Then
        assertEquals(expected = Result.success(Unit), actual = result)
    }

    @Test
    fun `GIVEN failure WHEN updateScene THEN result is failure`() = runTest {
        // Given
        val error = IOException()
        val repository = initRepository(
            storyWriterDatabase = object : StoryWriterDatabaseFake() {
                override fun sceneDao(): SceneDao = object : SceneDaoFake() {
                    override suspend fun update(scene: SceneDb) = throw error
                }
            }
        )

        // When
        val result = repository.updateScene(scene = Random.nextScene())

        // Then
        assertEquals(expected = Result.failure(error), actual = result)
    }

    @Test
    fun `GIVEN success WHEN updateScene THEN result is success`() = runTest {
        // Given
        val scene = Random.nextScene()
        val repository = initRepository()

        // When
        val result = repository.updateScene(scene = scene)

        // Then
        assertEquals(expected = Result.success(Unit), actual = result)
    }


    private fun initRepository(
        storyWriterDatabase: StoryWriterDatabase = StoryWriterDatabaseFake(),
    ): SceneRepository =
        SceneRepositoryImpl(
            storyWriterDatabase = storyWriterDatabase,
        )
}