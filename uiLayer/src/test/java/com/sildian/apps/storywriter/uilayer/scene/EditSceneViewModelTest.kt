package com.sildian.apps.storywriter.uilayer.scene

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.sildian.apps.storywriter.domainlayer.scene.SaveSceneUseCase
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

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

    @Test
    fun `GIVEN SaveScene intent while scene is not edited WHEN onIntent THEN do nothing`() = runTest {
        // Given
        var isSavedSceneUseCaseCalled = false
        val saveSceneUseCase = SaveSceneUseCase {
            isSavedSceneUseCaseCalled = true
            Result.success(value = Unit)
        }
        val viewModel = initViewModel(saveSceneUseCase = saveSceneUseCase)

        // When
        viewModel.onIntent(EditSceneViewModel.Intent.SaveScene)

        // Then
        viewModel.state.test {
            val expectedState = EditSceneViewModel.State()
            assertFalse(isSavedSceneUseCaseCalled)
            assertEquals(expected = expectedState, actual = awaitItem())
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `GIVEN failing SaveScene intent WHEN onIntent THEN save attempt is made, state is still edited and failure event is raised`() = runTest {
        // Given
        var isSavedSceneUseCaseCalled = false
        val saveSceneUseCase = SaveSceneUseCase {
            isSavedSceneUseCaseCalled = true
            Result.failure(exception = Exception())
        }
        val viewModel = initViewModel(saveSceneUseCase = saveSceneUseCase)
        val sceneDescription = "This is a scene."

        // When
        viewModel.onIntent(EditSceneViewModel.Intent.EditScene(sceneDescription = sceneDescription))
        viewModel.onIntent(EditSceneViewModel.Intent.SaveScene)

        // Then
        viewModel.state.test {
            val expectedState = EditSceneViewModel.State(
                sceneDescription = sceneDescription,
                isEdited = true,
            )
            assertTrue(isSavedSceneUseCaseCalled)
            assertEquals(expected = expectedState, actual = awaitItem())
            cancelAndConsumeRemainingEvents()
        }
        viewModel.event.test {
            val expectedEvent = EditSceneViewModel.Event.SaveSceneFailure
            assertEquals(expected = expectedEvent, actual = awaitItem())
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `GIVEN succeeding SaveScene intent while scene is edited WHEN onIntent THEN save attempt is made, state is not edited anymore and success event is raised`() = runTest {
        // Given
        var isSaveSceneUseCaseCalled = false
        val saveSceneUseCase = SaveSceneUseCase {
            isSaveSceneUseCaseCalled = true
            Result.success(value = Unit)
        }
        val viewModel = initViewModel(saveSceneUseCase = saveSceneUseCase)
        val sceneDescription = "This is a scene."

        // When
        viewModel.onIntent(EditSceneViewModel.Intent.EditScene(sceneDescription = sceneDescription))
        viewModel.onIntent(EditSceneViewModel.Intent.SaveScene)

        // Then
        viewModel.state.test {
            val expectedState = EditSceneViewModel.State(
                sceneDescription = sceneDescription,
                isEdited = false,
            )
            assertTrue(isSaveSceneUseCaseCalled)
            assertEquals(expected = expectedState, actual = awaitItem())
            cancelAndConsumeRemainingEvents()
        }
        viewModel.event.test {
            val expectedEvent = EditSceneViewModel.Event.SaveSceneSuccess
            assertEquals(expected = expectedEvent, actual = awaitItem())
            cancelAndConsumeRemainingEvents()
        }
    }

    private fun initViewModel(
        saveSceneUseCase: SaveSceneUseCase = SaveSceneUseCase { Result.success(value = Unit) },
    ): EditSceneViewModel =
        EditSceneViewModel(
            savedStateHandle = SavedStateHandle(),
            saveSceneUseCase = saveSceneUseCase,
        )
}