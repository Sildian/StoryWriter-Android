package com.sildian.apps.storywriter.uilayer

import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.NavigableListDetailPaneScaffold
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import com.sildian.apps.storywriter.uilayer.scene.editscene.EditSceneEntryPoint
import com.sildian.apps.storywriter.uilayer.scene.showscenes.ShowScenesEntryPoint
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun MainEntryPoint() {
    val scaffoldNavigator = rememberListDetailPaneScaffoldNavigator<Any>()
    val coroutineScope = rememberCoroutineScope()
    NavigableListDetailPaneScaffold(
        navigator = scaffoldNavigator,
        listPane = {
            AnimatedPane {
                ShowScenesEntryPoint(
                    onAddSceneButtonClick = {
                        coroutineScope.launch {
                            scaffoldNavigator.navigateTo(pane = ListDetailPaneScaffoldRole.Detail)
                        }
                    },
                )
            }
        },
        detailPane = {
            AnimatedPane {
                EditSceneEntryPoint()
            }
        }
    )
}