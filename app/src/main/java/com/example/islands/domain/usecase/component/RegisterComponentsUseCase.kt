package com.example.islands.domain.usecase.component

import com.example.islands.domain.model.component.ComponentIdentifier
import com.example.islands.domain.repository.component.ComponentRepository

class RegisterComponentsUseCase(
    private val componentRepository: ComponentRepository
) {
    operator fun invoke(identifiers: Set<ComponentIdentifier>) {
        componentRepository.register(identifiers)
    }
}
