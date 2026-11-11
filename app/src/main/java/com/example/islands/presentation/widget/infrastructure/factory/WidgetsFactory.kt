package com.example.islands.presentation.widget.infrastructure.factory

import com.example.islands.presentation.widget.infrastructure.contract.Widget

interface WidgetsFactory {
    fun getOrderedWidgets(): List<Widget>
}