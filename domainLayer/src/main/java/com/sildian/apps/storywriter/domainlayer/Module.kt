package com.sildian.apps.storywriter.domainlayer

import com.sildian.apps.storywriter.domainlayer.scene.SaveSceneUseCase
import com.sildian.apps.storywriter.domainlayer.scene.SaveSceneUseCaseImpl
import org.koin.dsl.module

val domainLayerModule = module {
    factory<SaveSceneUseCase> { SaveSceneUseCaseImpl(sceneRepository = get()) }
}