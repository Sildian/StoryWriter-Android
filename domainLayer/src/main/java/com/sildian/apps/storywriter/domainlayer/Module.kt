package com.sildian.apps.storywriter.domainlayer

import com.sildian.apps.storywriter.domainlayer.scene.GetAllScenesUseCase
import com.sildian.apps.storywriter.domainlayer.scene.GetAllScenesUseCaseImpl
import com.sildian.apps.storywriter.domainlayer.scene.SaveSceneUseCase
import com.sildian.apps.storywriter.domainlayer.scene.SaveSceneUseCaseImpl
import org.koin.dsl.module

val domainLayerModule = module {
    factory<GetAllScenesUseCase> { GetAllScenesUseCaseImpl(sceneRepository = get()) }
    factory<SaveSceneUseCase> { SaveSceneUseCaseImpl(sceneRepository = get()) }
}