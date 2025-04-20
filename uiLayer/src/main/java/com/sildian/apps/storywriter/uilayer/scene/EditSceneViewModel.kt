package com.sildian.apps.storywriter.uilayer.scene

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.parcelize.Parcelize

internal class EditSceneViewModel(
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    val state: StateFlow<State> = savedStateHandle.getStateFlow(
        key = KEY_STATE,
        initialValue = State(),
    )

    fun onIntent(intent: Intent) {
        when (intent) {
            is Intent.EditScene -> {
                savedStateHandle[KEY_STATE] = state.value.copy(
                    sceneDescription = intent.sceneDescription,
                    isEdited = intent.sceneDescription.isNotBlank(),
                )
            }
        }
    }

    @Parcelize
    data class State(
        val sceneDescription: String = "",
        val isEdited: Boolean = false,
    ) : Parcelable

    sealed interface Intent {
        data class EditScene(val sceneDescription: String) : Intent
    }

    companion object {
        private const val KEY_STATE = "key_edit_scene_state"
    }
}