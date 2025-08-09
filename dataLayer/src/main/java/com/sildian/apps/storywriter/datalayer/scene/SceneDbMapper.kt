package com.sildian.apps.storywriter.datalayer.scene

import com.sildian.apps.storywriter.domainlayer.scene.Scene

internal fun Scene.toDb(): SceneDb =
    SceneDb(
        id = id,
        description = description,
    )
