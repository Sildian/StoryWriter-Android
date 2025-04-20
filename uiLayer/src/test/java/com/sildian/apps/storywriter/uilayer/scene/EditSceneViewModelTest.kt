package com.sildian.apps.storywriter.uilayer.scene

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class EditSceneViewModelTest {

    @Test
    fun `GIVEN WHEN init THEN state is initialized`() = runTest {
        // Given When
        val viewModel = initViewModel()

        // Then
        viewModel.state.test {
            val expectedState = EditSceneViewModel.State()
            assertEquals(expected = expectedState, actual = awaitItem())
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `GIVEN EditScene intent with a blank text WHEN onIntent THEN state is updated and not edited`() = runTest {
        // Given
        val viewModel = initViewModel()
        val sceneDescription = " "

        // When
        viewModel.onIntent(EditSceneViewModel.Intent.EditScene(sceneDescription = sceneDescription))

        // Then
        viewModel.state.test {
            val expectedState = EditSceneViewModel.State(
                sceneDescription = sceneDescription,
                isEdited = false,
            )
            assertEquals(expected = expectedState, actual = awaitItem())
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `GIVEN EditScene intent with a text WHEN onIntent THEN state is updated and edited`() = runTest {
        // Given
        val viewModel = initViewModel()
        val sceneDescription = "This is a scene."

        // When
        viewModel.onIntent(EditSceneViewModel.Intent.EditScene(sceneDescription = sceneDescription))

        // Then
        viewModel.state.test {
            val expectedState = EditSceneViewModel.State(
                sceneDescription = sceneDescription,
                isEdited = true,
            )
            assertEquals(expected = expectedState, actual = awaitItem())
            cancelAndConsumeRemainingEvents()
        }
    }

    private fun initViewModel(): EditSceneViewModel =
        EditSceneViewModel(savedStateHandle = SavedStateHandle())
}