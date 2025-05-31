package com.sildian.apps.storywriter.datalayer

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import java.io.IOException

class InMemoryDatabaseRule<T: RoomDatabase>(
    val context: Context,
    private val klass: Class<T>,
) : TestWatcher() {

    lateinit var database: T

    override fun starting(description: Description?) {
        database = Room.inMemoryDatabaseBuilder(context = context, klass = klass).build()
    }

    @Throws(IOException::class)
    override fun finished(description: Description?) {
        database.close()
    }
}