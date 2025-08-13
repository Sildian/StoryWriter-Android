package com.sildian.apps.storywriter.uilayer.scene.showscenes

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.sildian.apps.storywriter.designsystem.theme.StoryWriterTheme
import com.sildian.apps.storywriter.uilayer.R
import com.sildian.apps.storywriter.uilayer.scene.SceneUi

@Composable
internal fun ShowScenesScreen(
    state: ShowScenesViewModel.State,
) {
    Scaffold(
        contentWindowInsets = WindowInsets.safeDrawing,
    ) { contentPadding ->
        Crossfade(targetState = state) { currentState ->
            val contentModifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
                .padding(16.dp)
            when (currentState) {
                is ShowScenesViewModel.State.Loading ->
                    LoadingContent(modifier = contentModifier)
                is ShowScenesViewModel.State.Failure ->
                    FailureContent(modifier = contentModifier)
                is ShowScenesViewModel.State.Success ->
                    SuccessContent(
                        modifier = contentModifier,
                        state = currentState,
                    )
            }
        }
    }
}

@Composable
private fun LoadingContent(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun FailureContent(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Icon(
                painter = painterResource(id = R.drawable.error_outline),
                tint = MaterialTheme.colorScheme.error,
                contentDescription = null,
            )
            Text(
                text = stringResource(id = R.string.show_scenes_message_load_scenes_failure),
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
            )
            Button(
                onClick = { /*TODO*/ },
            ) {
                Text(text = stringResource(id = R.string.show_scenes_button_retry))
            }
        }
    }
}

@Composable
private fun SuccessContent(
    state: ShowScenesViewModel.State.Success,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        items(items = state.scenes, key = { it.id }) { scene ->
            SceneItem(
                modifier = Modifier.fillMaxWidth(),
                scene = scene,
            )
        }
    }
}

@Preview
@Composable
private fun ShowScenesScreenPreview(
    @PreviewParameter(ShowScenesStatePreviewParamProvider::class) state: ShowScenesViewModel.State,
) {
    StoryWriterTheme {
        ShowScenesScreen(state = state)
    }
}

private class ShowScenesStatePreviewParamProvider : PreviewParameterProvider<ShowScenesViewModel.State> {
    override val values: Sequence<ShowScenesViewModel.State> =
        sequenceOf(
            ShowScenesViewModel.State.Loading,
            ShowScenesViewModel.State.Failure,
            ShowScenesViewModel.State.Success(
                scenes = List(size = 3) { index ->
                    SceneUi(
                        id = index.toLong(),
                        description = "Il était une fois un chevalier qui voulait entrer dans le château, mais l'accès lui était interdit. Comment diable pouvait-il faire pour se faire accepter ? Devait-il affronter un dragon ? C'était effrayant, mais il était résolu à faire tout ce qui était nécessaire."
                    )
                },
            )
        )
}