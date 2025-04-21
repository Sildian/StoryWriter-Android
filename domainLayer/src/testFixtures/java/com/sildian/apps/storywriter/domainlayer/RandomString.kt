package com.sildian.apps.storywriter.domainlayer

import kotlin.random.Random

fun Random.nextString(size: Int = nextInt(until = 20)): String =
    IntRange(start = 0, endInclusive = size).map {
        CharRange(start = '0', endInclusive = 'z').random()
    }.joinToString(separator = "")