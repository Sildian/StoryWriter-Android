package com.sildian.apps.storywriter.uilayer.scene

import com.sildian.apps.storywriter.domainlayer.scene.Scene
import com.sildian.apps.storywriter.domainlayer.scene.nextScene
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertEquals

class SceneUiMapperTest {

    @Test
    fun `map scene toUi should correctly map it`() {
        // Given
        val scene = Random.nextScene()

        // When
        val result = scene.toUi()

        // Then
        val expectedResult = SceneUi(
            id = scene.id,
            description = scene.description,
        )
        assertEquals(
            expected = expectedResult,
            actual = result,
        )
    }

    @Test
    fun `map sceneUi toDomain should correctly map it`() {
        // Given
        val sceneUi = Random.nextSceneUi()

        // When
        val result = sceneUi.toDomain()

        // Then
        val expectedResult = Scene(
            id = sceneUi.id,
            description = sceneUi.description,
        )
        assertEquals(
            expected = expectedResult,
            actual = result,
        )
    }
}