package com.sildian.apps.storywriter.datalayer.scene

import app.cash.turbine.test
import com.sildian.apps.storywriter.datalayer.StoryWriterDatabase
import com.sildian.apps.storywriter.datalayer.StoryWriterDatabaseFake
import com.sildian.apps.storywriter.domainlayer.scene.SceneRepository
import com.sildian.apps.storywriter.domainlayer.scene.nextScene
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import java.io.IOException
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertEquals

class SceneRepositoryImplTest {

    @Test
    fun `getAllScenes should fail when database fails`() = runTest {
        // Given
        val error = IOException()
        val repository = initRepository(
            storyWriterDatabase = object : StoryWriterDatabaseFake() {
                override fun sceneDao(): SceneDao = object : SceneDaoFake() {
                    override fun getAll(): Flow<List<SceneDb>> = flow { throw error }
                }
            }
        )

        // When
        repository.getAllScenes().test {

            // Then
            assertEquals(expected = error, actual = awaitError())
        }
    }

    @Test
    fun `getAllScenes should return all scenes when database succeeds`() = runTest {
        // Given
        val scenes = List(size = 3) { index -> Random.nextScene(id = index.toLong()) }
        val repository = initRepository(
            storyWriterDatabase = object : StoryWriterDatabaseFake() {
                override fun sceneDao(): SceneDao = object : SceneDaoFake() {
                    override fun getAll(): Flow<List<SceneDb>> = flowOf(scenes.map { it.toDb() })
                }
            }
        )

        // When
        repository.getAllScenes().test {

            // Then
            assertEquals(expected = scenes, actual = awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `saveScene should fail when database fails`() = runTest {
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
    fun `saveScene should insert the given scene when it is a new scene`() = runTest {
        // Given
        val scene = Random.nextScene(id = 0)
        val nextId: Long = Random.nextLong(from = 1, until = 100)
        var insertedScene: SceneDb? = null

        val repository = initRepository(
            storyWriterDatabase = object : StoryWriterDatabaseFake() {
                override fun sceneDao(): SceneDao = object : SceneDaoFake() {
                    override suspend fun insert(scene: SceneDb): Long {
                        insertedScene = scene
                        return nextId
                    }
                }
            }
        )

        // When
        val result = repository.saveScene(scene = scene)

        // Then
        assertEquals(expected = Result.success(nextId), actual = result)
        assertEquals(expected = scene.toDb(), actual = insertedScene)
    }

    @Test
    fun `saveScene should update the given scene when it is an existing scene`() = runTest {
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
        assertEquals(expected = Result.success(scene.id), actual = result)
        assertEquals(expected = scene.toDb(), actual = updatedScene)
    }

    private fun initRepository(
        storyWriterDatabase: StoryWriterDatabase = StoryWriterDatabaseFake(),
    ): SceneRepository =
        SceneRepositoryImpl(
            storyWriterDatabase = storyWriterDatabase,
        )
}