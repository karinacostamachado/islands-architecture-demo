package com.example.islands.domain.usecase.author

import com.example.islands.domain.model.book.Book
import com.example.islands.domain.repository.book.BookRepository
import kotlinx.coroutines.flow.Flow

class FetchBooksUseCase(
    private val bookRepository: BookRepository
) {
    operator fun invoke(): Flow<List<Book>> {
        return bookRepository.getBooks()
    }
}
