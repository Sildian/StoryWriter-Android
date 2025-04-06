package com.sildian.apps.storywriter.uilayer.scene

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.sildian.apps.storywriter.designsystem.theme.StoryWriterTheme

@Composable
internal fun EditSceneScreen() {
    Scaffold { contentPadding ->
        Text(
            modifier = Modifier.padding(paddingValues = contentPadding),
            text = "Edit Scene Screen",
        )
    }
}

@Preview
@Composable
private fun EditSceneScreenPreview() {
    StoryWriterTheme {
        EditSceneScreen()
    }
}