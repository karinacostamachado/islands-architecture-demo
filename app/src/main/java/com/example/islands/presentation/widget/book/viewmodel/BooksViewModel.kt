package com.example.islands.presentation.widget.book.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.islands.domain.model.component.ComponentState
import com.example.islands.domain.usecase.author.FetchBooksUseCase
import com.example.islands.presentation.widget.book.action.BooksAction
import com.example.islands.presentation.widget.book.effect.BooksUiEffect
import com.example.islands.presentation.widget.book.model.BookUiModel
import com.example.islands.presentation.widget.book.model.BooksUiModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BooksViewModel(
    private val fetchBooksUseCase: FetchBooksUseCase
) : ViewModel() {

    private val _uiModel = MutableStateFlow(BooksUiModel())
    val uiModel: StateFlow<BooksUiModel> = _uiModel.asStateFlow()

    private val _uiEffect = MutableSharedFlow<BooksUiEffect>(replay = 1)
    val uiEffect: SharedFlow<BooksUiEffect> = _uiEffect.asSharedFlow()

    fun handleAction(action: BooksAction) {
        when (action) {
            is BooksAction.OnInit -> onStateChange(action.state)
        }
    }

    private fun onStateChange(state: ComponentState) {
        when (state) {
            ComponentState.REGISTERED -> fetchBooks()
            else -> Unit
        }
    }

    private fun fetchBooks() {
        viewModelScope.launch {
            fetchBooksUseCase()
                .map { books ->
                    books.map { book ->
                        BookUiModel(
                            title = book.title,
                            author = book.author,
                            year = book.year,
                            genre = book.genre
                        )
                    }
                }
                .onStart {
                    _uiEffect.emit(BooksUiEffect.OnStateChanged(ComponentState.LOADING))
                }
                .catch {
                    _uiEffect.emit(BooksUiEffect.OnStateChanged(ComponentState.ERROR))
                }
                .collect { books ->
                    _uiModel.update {
                        it.copy(
                            books = books
                        )
                    }
                    _uiEffect.emit(BooksUiEffect.OnStateChanged(ComponentState.READY_TO_DISPLAY))
                }
        }
    }
}
