package com.sildian.apps.storywriter.uilayer.scene.showscenes

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sildian.apps.storywriter.domainlayer.scene.GetAllScenesUseCase
import com.sildian.apps.storywriter.uilayer.scene.SceneUi
import com.sildian.apps.storywriter.uilayer.scene.toUi
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize

internal class ShowScenesViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val getAllScenesUseCase: GetAllScenesUseCase,
) : ViewModel() {

    val state: StateFlow<State> = savedStateHandle.getStateFlow(
        key = KEY_STATE,
        initialValue = State.Loading,
    )

    init {
        loadScenes()
    }

    private fun loadScenes() {
        viewModelScope.launch {
            getAllScenesUseCase()
                .catch {
                    savedStateHandle[KEY_STATE] = State.Failure
                }.collect { scenes ->
                    savedStateHandle[KEY_STATE] = State.Success(scenes = scenes.map { it.toUi() })
                }
        }
    }

    sealed interface State : Parcelable {
        @Parcelize
        data object Loading : State
        @Parcelize
        data object Failure : State
        @Parcelize
        data class Success(val scenes: List<SceneUi>) : State
    }

    companion object {
        private const val KEY_STATE = "key_show_scenes_state"
    }
}