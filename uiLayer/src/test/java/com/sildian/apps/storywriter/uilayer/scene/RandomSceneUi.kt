package com.sildian.apps.storywriter.uilayer.scene

import com.sildian.apps.storywriter.domainlayer.nextString
import kotlin.random.Random

internal fun Random.nextSceneUi(
    id: Long = nextLong(from = 1, until = 100),
    description: String = nextString(),
): SceneUi =
    SceneUi(
        id = id,
        description = description,
    )