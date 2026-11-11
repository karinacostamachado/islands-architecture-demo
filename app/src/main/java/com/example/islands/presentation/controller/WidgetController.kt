package com.example.islands.presentation.controller

import com.example.islands.domain.model.component.ComponentIdentifier
import com.example.islands.domain.model.component.ComponentState
import com.example.islands.domain.usecase.component.ObserveComponentsUseCase
import com.example.islands.domain.usecase.component.UpdateComponentStateUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.mapNotNull

class WidgetController(
    observeComponentsUseCase: ObserveComponentsUseCase,
    private val updateComponentStateUseCase: UpdateComponentStateUseCase
) {
    val widgets: StateFlow<Map<ComponentIdentifier, ComponentState>> = observeComponentsUseCase()
    private val componentStates = mutableMapOf<ComponentIdentifier, Flow<ComponentState>>()

    fun getWidgetState(componentIdentifier: ComponentIdentifier): Flow<ComponentState> {
        return componentStates.getOrPut(componentIdentifier) {
            widgets.mapNotNull { states -> states[componentIdentifier] }
                .distinctUntilChanged()
        }
    }

    fun updateStateTo(componentIdentifier: ComponentIdentifier, state: ComponentState) {
        updateComponentStateUseCase(componentIdentifier, state)
    }
}
