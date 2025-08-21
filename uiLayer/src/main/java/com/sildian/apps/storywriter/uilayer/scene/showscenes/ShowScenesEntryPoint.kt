package com.sildian.apps.storywriter.uilayer.scene.showscenes

import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sildian.apps.storywriter.uilayer.scene.SceneUi
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun ShowScenesEntryPoint(
    onAddSceneButtonClick: () -> Unit,
    onEditSceneClick: (SceneUi) -> Unit,
    viewModel: ShowScenesViewModel = koinViewModel<ShowScenesViewModel>(),
) {
    ShowScenesScreen(
        state = viewModel.state.collectAsStateWithLifecycle().value,
        onAddSceneButtonClick = onAddSceneButtonClick,
        onEditSceneClick = onEditSceneClick,
    )
}