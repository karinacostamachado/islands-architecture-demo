package com.example.islands.domain.repository.book

import com.example.islands.domain.model.book.Book
import kotlinx.coroutines.flow.Flow

interface BookRepository {
    fun getBooks(): Flow<List<Book>>
}