package com.sildian.apps.storywriter.uilayer.scene

import com.sildian.apps.storywriter.domainlayer.scene.Scene
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertEquals

class SceneUiMapperTest {

    @Test
    fun `GIVEN any SceneUi WHEN mapping toDomain THEN Scene is returned`() {
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