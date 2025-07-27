package com.sildian.apps.storywriter.uilayer.scene

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sildian.apps.storywriter.domainlayer.scene.SaveSceneUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize

internal class EditSceneViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val saveSceneUseCase: SaveSceneUseCase,
) : ViewModel() {

    val state: StateFlow<State> = savedStateHandle.getStateFlow(
        key = KEY_STATE,
        initialValue = State(),
    )

    private val _event = Channel<Event>()
    val event: Flow<Event> = _event.receiveAsFlow()

    fun onIntent(intent: Intent) {
        when (intent) {
            is Intent.EditScene ->
                editScene(sceneDescription = intent.sceneDescription)
            is Intent.SaveScene ->
                saveScene()
        }
    }

    private fun editScene(sceneDescription: String) {
        val currentScene = state.value.scene
        savedStateHandle[KEY_STATE] = state.value.copy(
            scene = currentScene.copy(description = sceneDescription),
            isEdited = sceneDescription.isNotBlank(),
        )
    }

    private fun saveScene() {
        if (!state.value.isEdited) return
        viewModelScope.launch {
            val currentScene = state.value.scene
            savedStateHandle[KEY_STATE] = state.value.copy(isSaveSceneInProgress = true)
            saveSceneUseCase(scene = state.value.scene.toDomain())
                .onFailure {
                    savedStateHandle[KEY_STATE] = state.value.copy(isSaveSceneInProgress = false)
                    _event.send(Event.SaveSceneFailure)
                }.onSuccess { id ->
                    savedStateHandle[KEY_STATE] = state.value.copy(
                        scene = currentScene.copy(id = id),
                        isEdited = false,
                        isSaveSceneInProgress = false,
                    )
                    _event.send(Event.SaveSceneSuccess)
                }
        }
    }

    @Parcelize
    data class State(
        val scene: SceneUi = SceneUi(),
        val isEdited: Boolean = false,
        val isSaveSceneInProgress: Boolean = false,
    ) : Parcelable

    sealed interface Event {
        data object SaveSceneFailure : Event
        data object SaveSceneSuccess : Event
    }

    sealed interface Intent {
        data class EditScene(val sceneDescription: String) : Intent
        data object SaveScene : Intent
    }

    companion object {
        private const val KEY_STATE = "key_edit_scene_state"
    }
}