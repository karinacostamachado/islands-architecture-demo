package com.example.islands.presentation.widget.book.effect

import com.example.islands.domain.model.component.ComponentState

sealed interface BooksUiEffect {
    data class OnStateChanged(val state: ComponentState) : BooksUiEffect
}
