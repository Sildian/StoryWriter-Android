package com.sildian.apps.storywriter.uilayer

import com.sildian.apps.storywriter.uilayer.scene.editscene.EditSceneViewModel
import com.sildian.apps.storywriter.uilayer.scene.showscenes.ShowScenesViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val uiLayerModule = module {
    viewModel { ShowScenesViewModel(savedStateHandle = get(), getAllScenesUseCase = get()) }
    viewModel { EditSceneViewModel(savedStateHandle = get(), saveSceneUseCase = get()) }
}