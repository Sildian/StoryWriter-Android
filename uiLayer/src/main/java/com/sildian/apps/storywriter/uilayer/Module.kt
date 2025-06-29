package com.sildian.apps.storywriter.uilayer

import com.sildian.apps.storywriter.uilayer.scene.EditSceneViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val uiLayerModule = module {
    viewModel { EditSceneViewModel(savedStateHandle = get(), saveSceneUseCase = get()) }
}