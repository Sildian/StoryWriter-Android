package com.sildian.apps.storywriter.datalayer.scene

import kotlin.random.Random

internal open class SceneDaoFake : SceneDao {

    override suspend fun insert(scene: SceneDb) = Unit

    override suspend fun update(scene: SceneDb) = Unit

    override suspend fun get(id: Int): SceneDb = Random.nextSceneDb()
}