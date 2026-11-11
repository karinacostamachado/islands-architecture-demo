package com.example.islands.domain.usecase.component

import com.example.islands.domain.model.component.ComponentIdentifier
import com.example.islands.domain.model.component.ComponentState
import com.example.islands.domain.repository.component.ComponentRepository

class UpdateComponentStateUseCase(
    private val componentRepository: ComponentRepository
) {
    operator fun invoke(componentIdentifier: ComponentIdentifier, state: ComponentState) {
        componentRepository.updateStateTo(componentIdentifier, state)
    }
}
