package com.sildian.apps.storywriter.uilayer.scene.showscenes

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.sildian.apps.storywriter.domainlayer.scene.GetAllScenesUseCase
import com.sildian.apps.storywriter.domainlayer.scene.nextScene
import com.sildian.apps.storywriter.uilayer.scene.toUi
import com.sildian.apps.storywriter.uilayer.testutils.CoroutineTestRule
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import java.io.IOException
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertEquals

class ShowScenesViewModelTest {

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    @Test
    fun `init should raise Failure state when useCase fails`() = runTest {
        // Given
        val exception = IOException()
        val getAllScenesUseCase = GetAllScenesUseCase { flow { throw exception } }

        // When
        val viewModel = initViewModel(getAllScenesUseCase = getAllScenesUseCase)

        // Then
        viewModel.state.test {
            val expectedState = ShowScenesViewModel.State.Failure
            assertEquals(expected = expectedState, actual = awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `init should raise Success state with given scenes when useCase succeeds`() = runTest {
        // Given
        val scenes = List(size = 3) { index -> Random.nextScene(id = index.toLong()) }
        val getAllScenesUseCase = GetAllScenesUseCase { flowOf(scenes) }

        // When
        val viewModel = initViewModel(getAllScenesUseCase = getAllScenesUseCase)

        // Then
        viewModel.state.test {
            val expectedState = ShowScenesViewModel.State.Success(scenes = scenes.map { it.toUi() })
            assertEquals(expected = expectedState, actual = awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    private fun initViewModel(
        savedStateHandle: SavedStateHandle = SavedStateHandle(),
        getAllScenesUseCase: GetAllScenesUseCase = GetAllScenesUseCase {
            flowOf(List(size = 3) { index -> Random.nextScene(id = index.toLong()) })
        },
    ): ShowScenesViewModel =
        ShowScenesViewModel(
            savedStateHandle = savedStateHandle,
            getAllScenesUseCase = getAllScenesUseCase,
        )
}