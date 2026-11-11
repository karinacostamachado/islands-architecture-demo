package com.example.islands.presentation.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.islands.domain.model.component.ComponentIdentifier
import com.example.islands.domain.model.component.ComponentState
import com.example.islands.domain.usecase.component.RegisterComponentsUseCase
import com.example.islands.presentation.controller.WidgetController
import com.example.islands.presentation.home.action.HomeAction
import com.example.islands.presentation.widget.infrastructure.contract.Widget
import com.example.islands.presentation.widget.infrastructure.factory.WidgetsFactory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val registerComponentsUseCase: RegisterComponentsUseCase,
    widgetsFactory: WidgetsFactory,
    private val widgetController: WidgetController
) : ViewModel() {

    val widgets: List<Widget> = widgetsFactory.getOrderedWidgets()

    fun getWidgetState(identifier: ComponentIdentifier): Flow<ComponentState> {
        return widgetController.getWidgetState(identifier)
    }

    fun handleAction(action: HomeAction) {
        when (action) {
            HomeAction.OnLaunch -> {
                viewModelScope.launch {
                    val identifiers = widgets.map { it.identifier }.toSet()
                    registerComponentsUseCase(identifiers)
                }
            }
            is HomeAction.UpdateWidgetState -> {
                widgetController.updateStateTo(action.identifier, action.state)
            }
        }
    }
}
