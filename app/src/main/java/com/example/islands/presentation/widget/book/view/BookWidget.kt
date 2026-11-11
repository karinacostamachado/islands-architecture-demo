package com.example.islands.presentation.widget.book.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.islands.domain.model.component.ComponentIdentifier
import com.example.islands.domain.model.component.ComponentState
import com.example.islands.presentation.theme.IslandsTheme
import com.example.islands.presentation.widget.book.action.BooksAction
import com.example.islands.presentation.widget.book.effect.BooksUiEffect
import com.example.islands.presentation.widget.book.model.BookUiModel
import com.example.islands.presentation.widget.book.viewmodel.BooksViewModel
import com.example.islands.presentation.widget.infrastructure.contract.Widget
import kotlinx.coroutines.flow.Flow
import org.koin.androidx.compose.koinViewModel

class BookWidget : Widget {
    override val identifier: ComponentIdentifier
        get() = ComponentIdentifier.BOOKS

    @Composable
    override fun Render(
        state: Flow<ComponentState>,
        updateState: (ComponentState) -> Unit
    ) {
        val viewModel: BooksViewModel = koinViewModel()
        val uiModel by viewModel.uiModel.collectAsState()
        val stateValue by state.collectAsState(initial = ComponentState.LOADING)

        LaunchedEffect(state) {
            state.collect {
                viewModel.handleAction(BooksAction.OnInit(it))
            }
        }

        LaunchedEffect(Unit) {
            viewModel.uiEffect.collect { effect ->
                when (effect) {
                    is BooksUiEffect.OnStateChanged -> updateState(effect.state)
                }
            }
        }


        when (stateValue) {
            ComponentState.LOADING -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            ComponentState.READY_TO_DISPLAY, ComponentState.DISPLAYED -> {
                LaunchedEffect(stateValue) {
                    if (stateValue == ComponentState.READY_TO_DISPLAY) {
                        updateState(ComponentState.DISPLAYED)
                    }
                }
                BookList(books = uiModel.books)
            }
            ComponentState.ERROR -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Failed to load books",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }

            else -> Unit
        }
    }
}

@Composable
fun BookList(
    books: List<BookUiModel>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(
            items = books,
            key = { book -> "${book.title}_${book.author}" }
        ) { book ->
            BookCardComponent(
                title = book.title,
                author = book.author,
                year = book.year,
                genre = book.genre
            )
        }
    }
}

@Composable
fun BookCardComponent(
    title: String,
    author: String,
    year: Int,
    genre: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "By $author",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = genre,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = year.toString(),
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun BookCardComponentPreview() {
    IslandsTheme {
        BookCardComponent(
            title = "The Hobbit",
            author = "J.R.R. Tolkien",
            year = 1937,
            genre = "Fantasy",
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun BookListPreview() {
    IslandsTheme {
        BookList(
            books = listOf(
                BookUiModel(
                    title = "The Hobbit",
                    author = "J.R.R. Tolkien",
                    year = 1937,
                    genre = "Fantasy"
                ),
                BookUiModel(
                    title = "1984",
                    author = "George Orwell",
                    year = 1949,
                    genre = "Dystopian"
                ),
                BookUiModel(
                    title = "To Kill a Mockingbird",
                    author = "Harper Lee",
                    year = 1960,
                    genre = "Classic"
                )
            )
        )
    }
}
