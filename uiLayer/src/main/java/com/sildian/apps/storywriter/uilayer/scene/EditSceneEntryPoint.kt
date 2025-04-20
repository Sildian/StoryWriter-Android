package com.sildian.apps.storywriter.uilayer.scene

import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun EditSceneEntryPoint(
    viewModel: EditSceneViewModel = koinViewModel<EditSceneViewModel>(),
) {
    EditSceneScreen(
        state = viewModel.state.collectAsStateWithLifecycle().value,
        onIntent = viewModel::onIntent,
    )
}