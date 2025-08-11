package com.sildian.apps.storywriter.datalayer.scene

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
internal interface SceneDao {

    @Insert
    suspend fun insert(scene: SceneDb): Long

    @Update
    suspend fun update(scene: SceneDb)

    @Query("SELECT * FROM SceneDb")
    suspend fun getAll(): List<SceneDb>

    @Query("SELECT * FROM SceneDb WHERE id = :id")
    suspend fun get(id: Long): SceneDb
}