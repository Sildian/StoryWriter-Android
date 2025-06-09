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
    fun `GIVEN failure WHEN saveScene THEN result is failure`() = runTest {
        // Given
        val error = IOException()
        val repository = initRepository(
            storyWriterDatabase = object : StoryWriterDatabaseFake() {
                override fun sceneDao(): SceneDao = object : SceneDaoFake() {
                    override suspend fun insert(scene: SceneDb) = throw error
                    override suspend fun update(scene: SceneDb) = throw error
                }
            }
        )

        // When
        val result = repository.saveScene(scene = Random.nextScene())

        // Then
        assertEquals(expected = Result.failure(error), actual = result)
    }

    @Test
    fun `GIVEN success and new scene WHEN saveScene THEN result is success and scene is inserted`() = runTest {
        // Given
        val scene = Random.nextScene(id = 0)
        var insertedScene: SceneDb? = null
        val repository = initRepository(
            storyWriterDatabase = object : StoryWriterDatabaseFake() {
                override fun sceneDao(): SceneDao = object : SceneDaoFake() {
                    override suspend fun insert(scene: SceneDb) {
                        insertedScene = scene
                    }
                }
            }
        )

        // When
        val result = repository.saveScene(scene = scene)

        // Then
        assertEquals(expected = Result.success(Unit), actual = result)
        assertEquals(expected = scene.toDb(), actual = insertedScene)
    }

    @Test
    fun `GIVEN success and existing scene WHEN saveScene THEN result is success and scene is updated`() = runTest {
        // Given
        val scene = Random.nextScene()
        var updatedScene: SceneDb? = null
        val repository = initRepository(
            storyWriterDatabase = object : StoryWriterDatabaseFake() {
                override fun sceneDao(): SceneDao = object : SceneDaoFake() {
                    override suspend fun update(scene: SceneDb) {
                        updatedScene = scene
                    }
                }
            }
        )

        // When
        val result = repository.saveScene(scene = scene)

        // Then
        assertEquals(expected = Result.success(Unit), actual = result)
        assertEquals(expected = scene.toDb(), actual = updatedScene)

    }

    private fun initRepository(
        storyWriterDatabase: StoryWriterDatabase = StoryWriterDatabaseFake(),
    ): SceneRepository =
        SceneRepositoryImpl(
            storyWriterDatabase = storyWriterDatabase,
        )
}