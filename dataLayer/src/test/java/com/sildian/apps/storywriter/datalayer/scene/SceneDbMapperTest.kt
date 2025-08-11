package com.sildian.apps.storywriter.datalayer.scene

import com.sildian.apps.storywriter.domainlayer.scene.Scene
import com.sildian.apps.storywriter.domainlayer.scene.nextScene
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertEquals

class SceneDbMapperTest {

    @Test
    fun `map scene toDb should correctly map it`() {
        // Given
        val scene = Random.nextScene()

        // When
        val result = scene.toDb()

        // Then
        val expectedResult = SceneDb(
            id = scene.id,
            description = scene.description,
        )
        assertEquals(
            expected = expectedResult,
            actual = result,
        )
    }

    @Test
    fun `map sceneDb toDomain should correctly map it`() {
        // Given
        val sceneDb = Random.nextSceneDb()

        // When
        val result = sceneDb.toDomain()

        // Then
        val expectedResult = Scene(
            id = sceneDb.id,
            description = sceneDb.description,
        )
        assertEquals(
            expected = expectedResult,
            actual = result,
        )
    }
}