package com.example.islands.presentation.widget.book.model

private const val EMPTY_STRING = ""

data class BooksUiModel(
    val books: List<BookUiModel> = emptyList()
)

data class BookUiModel(
    val title: String = EMPTY_STRING,
    val author: String = EMPTY_STRING,
    val year: Int = 2026,
    val genre: String = EMPTY_STRING
)
