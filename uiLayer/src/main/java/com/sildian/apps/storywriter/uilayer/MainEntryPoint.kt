package com.sildian.apps.storywriter.uilayer

import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.BackNavigationBehavior
import androidx.compose.material3.adaptive.navigation.NavigableListDetailPaneScaffold
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import com.sildian.apps.storywriter.uilayer.scene.SceneUi
import com.sildian.apps.storywriter.uilayer.scene.editscene.EditSceneEntryPoint
import com.sildian.apps.storywriter.uilayer.scene.showscenes.ShowScenesEntryPoint
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun MainEntryPoint() {
    val scaffoldNavigator = rememberListDetailPaneScaffoldNavigator<Any>()
    val coroutineScope = rememberCoroutineScope()
    NavigableListDetailPaneScaffold(
        navigator = scaffoldNavigator,
        defaultBackBehavior = BackNavigationBehavior.PopUntilScaffoldValueChange,
        listPane = {
            AnimatedPane {
                ShowScenesEntryPoint(
                    onAddSceneButtonClick = {
                        coroutineScope.launch {
                            scaffoldNavigator.navigateTo(pane = ListDetailPaneScaffoldRole.Detail)
                        }
                    },
                    onEditSceneClick = { scene ->
                        coroutineScope.launch {
                            scaffoldNavigator.navigateTo(
                                pane = ListDetailPaneScaffoldRole.Detail,
                                content = scene,
                            )
                        }
                    },
                )
            }
        },
        detailPane = {
            AnimatedPane {
                val scene = scaffoldNavigator.currentDestination?.content as? SceneUi
                EditSceneEntryPoint(
                    viewModel = if (scene != null) {
                        koinViewModel(
                            qualifier = named(QUALIFIER_EDIT_SCENE_VM_PARAM_SCENE),
                            key = scene.id.toString(),
                            parameters = { parametersOf(scene) },
                        )
                    } else {
                        koinViewModel()
                    },
                )
            }
        }
    )
}