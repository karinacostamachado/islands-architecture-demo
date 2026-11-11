package com.example.islands.data.repository.component

import com.example.islands.domain.model.component.ComponentIdentifier
import com.example.islands.domain.model.component.ComponentState
import com.example.islands.domain.repository.component.ComponentRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class ComponentRepositoryImpl : ComponentRepository {
    private val components: MutableStateFlow<Map<ComponentIdentifier, ComponentState>> =
        MutableStateFlow(emptyMap())

    override fun register(identifier: Set<ComponentIdentifier>) {
        components.update { current ->
            val registered = identifier
                .filterNot(current::containsKey)
                .associateWith { ComponentState.REGISTERED }
            if (registered.isEmpty()) current else current + registered
        }
    }

    override fun updateStateTo(
        identifier: ComponentIdentifier,
        state: ComponentState
    ) {
        components.update { current ->
            if (identifier !in current) current else current + (identifier to state)
        }
    }

    override fun observeComponents(): StateFlow<Map<ComponentIdentifier, ComponentState>> {
        return components
    }
}
