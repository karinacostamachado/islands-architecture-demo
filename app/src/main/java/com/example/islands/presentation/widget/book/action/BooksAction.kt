package com.example.islands.presentation.widget.book.action

import com.example.islands.domain.model.component.ComponentState

sealed interface BooksAction {
    data class OnInit(val state: ComponentState) : BooksAction
}
