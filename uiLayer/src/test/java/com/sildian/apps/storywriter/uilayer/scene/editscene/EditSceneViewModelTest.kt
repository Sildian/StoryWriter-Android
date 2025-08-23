package com.sildian.apps.storywriter.uilayer.scene.editscene

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.sildian.apps.storywriter.domainlayer.scene.SaveSceneUseCase
import com.sildian.apps.storywriter.uilayer.scene.SceneUi
import com.sildian.apps.storywriter.uilayer.scene.nextSceneUi
import com.sildian.apps.storywriter.uilayer.testutils.CoroutineTestRule
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class EditSceneViewModelTest {

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    @Test
    fun `state should be initialized after init`() = runTest {
        // Given When
        val viewModel = initViewModel()

        // Then
        viewModel.state.test {
            val expectedState = EditSceneViewModel.State()
            assertEquals(expected = expectedState, actual = awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `state should be initialized with initial scene when it is provided`() = runTest {
        // Given
        val initialScene = Random.nextSceneUi()

        // When
        val viewModel = initViewModel(
            savedStateHandle = SavedStateHandle(
                initialState = mapOf(EditSceneViewModel.KEY_INITIAL_SCENE to initialScene),
            ),
        )

        // Then
        viewModel.state.test {
            val expectedState = EditSceneViewModel.State(scene = initialScene)
            assertEquals(expected = expectedState, actual = awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `EditScene intent with a blank text should update the state to not edited`() = runTest {
        // Given
        val viewModel = initViewModel()
        val sceneDescription = " "

        // When
        viewModel.onIntent(EditSceneViewModel.Intent.EditScene(sceneDescription = sceneDescription))

        // Then
        viewModel.state.test {
            val expectedState = EditSceneViewModel.State(
                scene = SceneUi(description = sceneDescription),
                isEdited = false,
            )
            assertEquals(expected = expectedState, actual = awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `EditScene intent with a text should update state to edited`() = runTest {
        // Given
        val viewModel = initViewModel()
        val sceneDescription = "This is a scene."

        // When
        viewModel.onIntent(EditSceneViewModel.Intent.EditScene(sceneDescription = sceneDescription))

        // Then
        viewModel.state.test {
            val expectedState = EditSceneViewModel.State(
                scene = SceneUi(description = sceneDescription),
                isEdited = true,
            )
            assertEquals(expected = expectedState, actual = awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `SaveScene intent should do nothing when scene is not edited`() = runTest {
        // Given
        var isSavedSceneUseCaseCalled = false
        val saveSceneUseCase = SaveSceneUseCase {
            isSavedSceneUseCaseCalled = true
            Result.success(value = Random.Default.nextLong(from = 1, until = 100))
        }
        val viewModel = initViewModel(saveSceneUseCase = saveSceneUseCase)

        // When
        viewModel.onIntent(EditSceneViewModel.Intent.SaveScene)

        // Then
        viewModel.state.test {
            val expectedState = EditSceneViewModel.State()
            assertFalse(isSavedSceneUseCaseCalled)
            assertEquals(expected = expectedState, actual = awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `SaveScene intent should not change the state but raise a failure event when useCase fails`() =
        runTest {
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
                    scene = SceneUi(description = sceneDescription),
                    isEdited = true,
                )
                assertTrue(isSavedSceneUseCaseCalled)
                assertEquals(expected = expectedState, actual = awaitItem())
                cancelAndIgnoreRemainingEvents()
            }
            viewModel.event.test {
                val expectedEvent = EditSceneViewModel.Event.SaveSceneFailure
                assertEquals(expected = expectedEvent, actual = awaitItem())
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `SaveScene intent should update the state to not edited, update the scene id and raise a success event when useCase succeeds`() =
        runTest {
            // Given
            var isSaveSceneUseCaseCalled = false
            val sceneNextId = Random.Default.nextLong(from = 1, until = 100)
            val saveSceneUseCase = SaveSceneUseCase {
                isSaveSceneUseCaseCalled = true
                Result.success(value = sceneNextId)
            }
            val viewModel = initViewModel(saveSceneUseCase = saveSceneUseCase)
            val sceneDescription = "This is a scene."

            // When
            viewModel.onIntent(EditSceneViewModel.Intent.EditScene(sceneDescription = sceneDescription))
            viewModel.onIntent(EditSceneViewModel.Intent.SaveScene)

            // Then
            viewModel.state.test {
                val expectedState = EditSceneViewModel.State(
                    scene = SceneUi(id = sceneNextId, description = sceneDescription),
                    isEdited = false,
                )
                assertTrue(isSaveSceneUseCaseCalled)
                assertEquals(expected = expectedState, actual = awaitItem())
                cancelAndIgnoreRemainingEvents()
            }
            viewModel.event.test {
                val expectedEvent = EditSceneViewModel.Event.SaveSceneSuccess
                assertEquals(expected = expectedEvent, actual = awaitItem())
                cancelAndIgnoreRemainingEvents()
            }
        }

    private fun initViewModel(
        savedStateHandle: SavedStateHandle = SavedStateHandle(),
        saveSceneUseCase: SaveSceneUseCase = SaveSceneUseCase {
            Result.success(value = Random.Default.nextLong(from = 1, until = 100))
        },
    ): EditSceneViewModel =
        EditSceneViewModel(
            savedStateHandle = savedStateHandle,
            saveSceneUseCase = saveSceneUseCase,
        )
}