package com.sildian.apps.storywriter.uilayer.scene

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.union
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sildian.apps.storywriter.designsystem.theme.StoryWriterTheme
import com.sildian.apps.storywriter.uilayer.R

@Composable
internal fun EditSceneScreen(
    state: EditSceneViewModel.State,
    onIntent: (EditSceneViewModel.Intent) -> Unit,
) {
    Scaffold(
        contentWindowInsets = WindowInsets.safeDrawing,
        bottomBar = {
            BottomAppBar(
                contentPadding = PaddingValues(16.dp),
                windowInsets = BottomAppBarDefaults.windowInsets.union(WindowInsets.ime),
            ) {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { /*TODO*/ },
                    enabled = state.isEdited,
                ) {
                    Text(text = stringResource(id = R.string.edit_scene_button_save))
                }
            }
        }
    ) { contentPadding ->
        TextField(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
                .padding(16.dp),
            value = state.sceneDescription,
            onValueChange = { newValue ->
                onIntent(EditSceneViewModel.Intent.EditScene(sceneDescription = newValue))
            },
            label = {
                Text(text = stringResource(id = R.string.edit_scene_label_scene))
            },
            placeholder = {
                Text(text = stringResource(id = R.string.edit_scene_placeholder_scene))
            },
            keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences),
        )
    }
}

@Preview
@Composable
private fun EditSceneScreenPreview() {
    StoryWriterTheme {
        EditSceneScreen(
            state = EditSceneViewModel.State(
                sceneDescription = "This is a scene",
                isEdited = true,
            ),
            onIntent = { },
        )
    }
}