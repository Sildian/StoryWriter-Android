package com.sildian.apps.storywriter.datalayer.scene

import kotlin.random.Random

internal open class SceneDaoFake : SceneDao {

    override suspend fun insert(scene: SceneDb) = Random.nextLong(from = 1, until = 100)

    override suspend fun update(scene: SceneDb) = Unit

    override suspend fun get(id: Long): SceneDb = Random.nextSceneDb()
}