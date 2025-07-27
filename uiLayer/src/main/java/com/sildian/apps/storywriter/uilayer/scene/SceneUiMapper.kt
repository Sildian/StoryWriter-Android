package com.sildian.apps.storywriter.uilayer.scene

import com.sildian.apps.storywriter.domainlayer.scene.Scene

internal fun SceneUi.toDomain(): Scene =
    Scene(
        id = id,
        description = description,
    )
