package com.sildian.apps.storywriter.datalayer

import androidx.room.Room
import com.sildian.apps.storywriter.datalayer.scene.SceneRepositoryImpl
import com.sildian.apps.storywriter.domainlayer.scene.SceneRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataLayerModule = module {

    single<StoryWriterDatabase> {
        Room.databaseBuilder(
            context = androidContext(),
            klass = StoryWriterDatabase::class.java,
            name = "story_writer_db",
        ).build()
    }

    factory<SceneRepository> { SceneRepositoryImpl(storyWriterDatabase = get()) }
}