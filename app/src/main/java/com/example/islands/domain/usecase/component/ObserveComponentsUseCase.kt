package com.example.islands.domain.usecase.component

import com.example.islands.domain.model.component.ComponentIdentifier
import com.example.islands.domain.model.component.ComponentState
import com.example.islands.domain.repository.component.ComponentRepository
import kotlinx.coroutines.flow.StateFlow

class ObserveComponentsUseCase(
    private val componentRepository: ComponentRepository
) {
    operator fun invoke(): StateFlow<Map<ComponentIdentifier, ComponentState>> {
        return componentRepository.observeComponents()
    }
}
