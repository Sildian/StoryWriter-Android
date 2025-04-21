package com.sildian.apps.storywriter.domainlayer

import com.sildian.apps.storywriter.domainlayer.scene.AddSceneUseCase
import com.sildian.apps.storywriter.domainlayer.scene.AddSceneUseCaseImpl
import com.sildian.apps.storywriter.domainlayer.scene.UpdateSceneUseCase
import com.sildian.apps.storywriter.domainlayer.scene.UpdateSceneUseCaseImpl
import org.koin.dsl.module

val domainLayerModule = module {
    factory<AddSceneUseCase> { AddSceneUseCaseImpl(sceneRepository = get()) }
    factory<UpdateSceneUseCase> { UpdateSceneUseCaseImpl(sceneRepository = get()) }
}