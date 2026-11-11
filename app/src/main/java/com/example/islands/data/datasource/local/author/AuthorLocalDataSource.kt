package com.example.islands.data.datasource.local.author

import com.example.islands.domain.model.author.Author

class AuthorLocalDataSource {
    fun getAuthors(): List<Author> {
        return listOf(
            Author("George Orwell", "English", 1903, 1950, emptyList()),
            Author("J.R.R. Tolkien", "English", 1892, 1973, emptyList()),
            Author("F. Scott Fitzgerald", "American", 1896, 1940, emptyList()),
        )
    }
}