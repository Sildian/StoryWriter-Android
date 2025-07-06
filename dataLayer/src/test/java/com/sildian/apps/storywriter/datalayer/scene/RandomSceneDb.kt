package com.sildian.apps.storywriter.datalayer.scene

import com.sildian.apps.storywriter.domainlayer.nextString
import kotlin.random.Random

internal fun Random.nextSceneDb(
    id: Long = nextLong(from = 1, until = 100),
    description: String = nextString(),
): SceneDb = SceneDb(
    id = id,
    description = description,
)