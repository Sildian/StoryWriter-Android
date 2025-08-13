package com.sildian.apps.storywriter.uilayer.scene.showscenes

import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun ShowScenesEntryPoint(
    viewModel: ShowScenesViewModel = koinViewModel<ShowScenesViewModel>(),
) {
    ShowScenesScreen(state = viewModel.state.collectAsStateWithLifecycle().value)
}