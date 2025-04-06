package com.sildian.apps.storywriter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.sildian.apps.storywriter.designsystem.theme.StoryWriterTheme
import com.sildian.apps.storywriter.uilayer.scene.EditSceneEntryPoint

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            StoryWriterTheme {
                EditSceneEntryPoint()
            }
        }
    }
}