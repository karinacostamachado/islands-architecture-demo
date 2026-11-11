package com.example.islands.presentation.widget.infrastructure.factory

import com.example.islands.presentation.widget.infrastructure.contract.Widget

class WidgetsFactoryImpl(
    private val widgets: List<Widget>
) : WidgetsFactory {
    override fun getOrderedWidgets(): List<Widget> {
        return widgets.sortedBy { it.identifier.ordinal }
    }
}