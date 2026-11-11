package com.example.islands.domain.repository.component

import com.example.islands.domain.model.component.ComponentIdentifier
import com.example.islands.domain.model.component.ComponentState
import kotlinx.coroutines.flow.StateFlow

interface ComponentRepository {
    fun register(identifier: Set<ComponentIdentifier>)
    fun updateStateTo(identifier: ComponentIdentifier, state: ComponentState)
    fun observeComponents(): StateFlow<Map<ComponentIdentifier, ComponentState>>
}
