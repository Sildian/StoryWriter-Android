package com.sildian.apps.storywriter.uilayer.scene.showscenes

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sildian.apps.storywriter.designsystem.theme.StoryWriterTheme
import com.sildian.apps.storywriter.uilayer.R
import com.sildian.apps.storywriter.uilayer.scene.SceneUi

@Composable
internal fun SceneItem(
    scene: SceneUi,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Text(
        modifier = modifier
            .animateContentSize()
            .background(
                color = MaterialTheme.colorScheme.secondaryContainer,
                shape = MaterialTheme.shapes.large.copy(
                    topEnd = ZeroCornerSize,
                    bottomStart = ZeroCornerSize,
                ),
            ).clickable(
                role = Role.Button,
                onClickLabel = stringResource(id = R.string.show_scenes_action_edit_scene_content_description),
                onClick = onClick,
            )
            .padding(16.dp),
        text = scene.description,
        style = MaterialTheme.typography.bodyLarge,
        color = MaterialTheme.colorScheme.onSecondaryContainer,
        maxLines = 3,
        overflow = TextOverflow.Ellipsis,
    )
}

@Preview
@Composable
private fun SceneItemPreview() {
    StoryWriterTheme {
        SceneItem(
            scene = SceneUi(
                description = "Il était une fois un chevalier qui voulait séduire la princesse, mais le château dans lequel elle vivait était bien trop grand et il ne la trouvait pas. Il se demandait si combattre un dragon ne l'aiderait pas dans sa quête, bien qu'il n'y ait pas un grand rapport."
            ),
            onClick = { },
        )
    }
}