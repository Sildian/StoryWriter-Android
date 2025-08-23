package com.sildian.apps.storywriter.datalayer.scene

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlin.random.Random

internal open class SceneDaoFake : SceneDao {

    override suspend fun insert(scene: SceneDb) = Random.nextLong(from = 1, until = 100)

    override suspend fun update(scene: SceneDb) = Unit

    override fun getAll(): Flow<List<SceneDb>> = flowOf(
        List(size = Random.nextInt(from = 1, until = 4)) { index ->
            Random.nextSceneDb(id = index.toLong())
        }
    )

    override suspend fun get(id: Long): SceneDb = Random.nextSceneDb()
}