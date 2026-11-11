package com.example.islands.data.repository.author

import com.example.islands.data.datasource.local.author.AuthorLocalDataSource
import com.example.islands.domain.model.author.Author
import com.example.islands.domain.repository.author.AuthorRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AuthorRepositoryImpl(
    private val authorLocalDataSource: AuthorLocalDataSource
) : AuthorRepository {
    override fun getAuthors(): Flow<List<Author>> {
        return flow {
            emit(authorLocalDataSource.getAuthors())
        }
    }
}