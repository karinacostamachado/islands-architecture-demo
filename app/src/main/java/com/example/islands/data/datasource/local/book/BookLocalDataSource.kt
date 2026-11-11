package com.example.islands.data.datasource.local.book

import com.example.islands.domain.model.book.Book

class BookLocalDataSource {
    fun getBooks(): List<Book> {
        return listOf(
            Book("The Great Gatsby", "F. Scott Fitzgerald", 1925, "Classic"),
            Book("To Kill a Mockingbird", "Harper Lee", 1960, "Classic"),
            Book("1984", "George Orwell", 1949, "Dystopian"),
        )
    }
}