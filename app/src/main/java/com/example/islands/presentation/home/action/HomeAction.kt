package com.example.islands.presentation.home.action

import com.example.islands.domain.model.component.ComponentIdentifier
import com.example.islands.domain.model.component.ComponentState

sealed interface HomeAction {
    data object OnLaunch : HomeAction
    data class UpdateWidgetState(
        val identifier: ComponentIdentifier,
        val state: ComponentState
    ) : HomeAction
}
