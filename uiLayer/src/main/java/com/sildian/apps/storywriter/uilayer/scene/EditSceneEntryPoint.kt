package com.sildian.apps.storywriter.uilayer.scene

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sildian.apps.storywriter.uilayer.R
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun EditSceneEntryPoint(
    viewModel: EditSceneViewModel = koinViewModel<EditSceneViewModel>(),
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val saveSceneFailureMessage = stringResource(id = R.string.edit_scene_message_save_scene_failure)
    val saveSceneSuccessMessage = stringResource(id = R.string.edit_scene_message_save_scene_success)
    EditSceneScreen(
        snackbarHostState = snackbarHostState,
        state = viewModel.state.collectAsStateWithLifecycle().value,
        onIntent = viewModel::onIntent,
    )
    LaunchedEffect(Unit) {
        viewModel.event.collect { event ->
            when (event) {
                EditSceneViewModel.Event.SaveSceneFailure ->
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar(message = saveSceneFailureMessage)
                    }
                EditSceneViewModel.Event.SaveSceneSuccess ->
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar(message = saveSceneSuccessMessage)
                    }
            }
        }
    }
}