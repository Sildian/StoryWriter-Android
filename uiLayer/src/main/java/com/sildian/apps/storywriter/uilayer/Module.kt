package com.sildian.apps.storywriter.uilayer

import androidx.lifecycle.SavedStateHandle
import com.sildian.apps.storywriter.uilayer.scene.SceneUi
import com.sildian.apps.storywriter.uilayer.scene.editscene.EditSceneViewModel
import com.sildian.apps.storywriter.uilayer.scene.showscenes.ShowScenesViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

internal const val QUALIFIER_EDIT_SCENE_VM_PARAM_SCENE = "qualifier_edit_scene_vm_param_scene"

val uiLayerModule = module {
    viewModel { ShowScenesViewModel(savedStateHandle = get(), getAllScenesUseCase = get()) }
    viewModel { EditSceneViewModel(savedStateHandle = get(), saveSceneUseCase = get()) }
    viewModel(qualifier = named(QUALIFIER_EDIT_SCENE_VM_PARAM_SCENE)) { (scene: SceneUi) ->
        EditSceneViewModel(
            savedStateHandle = get<SavedStateHandle>().apply {
                set(
                    key = EditSceneViewModel.KEY_INITIAL_SCENE,
                    value = scene,
                )
            },
            saveSceneUseCase = get(),
        )
    }
}