package com.sildian.apps.storywriter.datalayer.scene

import com.sildian.apps.storywriter.domainlayer.scene.nextScene
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertEquals

class SceneMapperTest {

    @Test
    fun `GIVEN any Scene WHEN mapping toDb THEN SceneDb is returned`() {
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
}