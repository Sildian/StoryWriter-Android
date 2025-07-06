package com.sildian.apps.storywriter.domainlayer.scene

import com.sildian.apps.storywriter.domainlayer.nextString
import kotlin.random.Random

fun Random.nextScene(
    id: Long = nextLong(from = 1, until = 100),
    description: String = nextString(),
): Scene = Scene(
    id = id,
    description = description,
)