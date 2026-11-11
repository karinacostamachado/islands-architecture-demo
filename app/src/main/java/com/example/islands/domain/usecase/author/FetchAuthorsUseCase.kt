package com.example.islands.domain.usecase.author

import com.example.islands.domain.model.author.Author
import com.example.islands.domain.repository.author.AuthorRepository
import kotlinx.coroutines.flow.Flow

class FetchAuthorsUseCase(
    private val authorRepository: AuthorRepository
) {
    operator fun invoke(): Flow<List<Author>> {
        return authorRepository.getAuthors()
    }
}
