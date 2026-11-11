package com.example.islands.domain.model.author

import com.example.islands.domain.model.book.Book

data class Author(
    val name: String,
    val nationality: String,
    val birthYear: Int,
    val deathYear: Int?,
    val publishedBooks: List<Book>
)