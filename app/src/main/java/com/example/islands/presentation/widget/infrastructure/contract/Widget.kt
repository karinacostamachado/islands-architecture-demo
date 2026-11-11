package com.example.islands.presentation.widget.infrastructure.contract

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import com.example.islands.domain.model.component.ComponentIdentifier
import com.example.islands.domain.model.component.ComponentState
import kotlinx.coroutines.flow.Flow

@Stable
interface Widget {

    val identifier: ComponentIdentifier

    @Composable
    fun Render(
        state: Flow<ComponentState>,
        updateState: (ComponentState) -> Unit
    )
}
