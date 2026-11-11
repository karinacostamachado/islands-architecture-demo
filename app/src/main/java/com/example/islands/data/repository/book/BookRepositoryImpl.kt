package com.example.islands.data.repository.book

import com.example.islands.data.datasource.local.book.BookLocalDataSource
import com.example.islands.domain.model.book.Book
import com.example.islands.domain.repository.book.BookRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.time.Duration.Companion.milliseconds

class BookRepositoryImpl(
    private val bookLocalDataSource: BookLocalDataSource
) : BookRepository {
    override fun getBooks(): Flow<List<Book>> {
        return flow {
            delay(1000.milliseconds)
            emit(bookLocalDataSource.getBooks())
        }
    }
}