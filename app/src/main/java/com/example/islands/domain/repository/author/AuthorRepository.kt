package com.example.islands.domain.repository.author

import com.example.islands.domain.model.author.Author
import kotlinx.coroutines.flow.Flow

interface AuthorRepository {
    fun getAuthors(): Flow<List<Author>>
}