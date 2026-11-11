package com.example.islands.di

import com.example.islands.data.datasource.local.author.AuthorLocalDataSource
import com.example.islands.data.datasource.local.book.BookLocalDataSource
import com.example.islands.data.repository.author.AuthorRepositoryImpl
import com.example.islands.data.repository.book.BookRepositoryImpl
import com.example.islands.data.repository.component.ComponentRepositoryImpl
import com.example.islands.domain.repository.author.AuthorRepository
import com.example.islands.domain.repository.book.BookRepository
import com.example.islands.domain.repository.component.ComponentRepository
import com.example.islands.domain.usecase.author.FetchAuthorsUseCase
import com.example.islands.domain.usecase.author.FetchBooksUseCase
import com.example.islands.domain.usecase.component.ObserveComponentsUseCase
import com.example.islands.domain.usecase.component.RegisterComponentsUseCase
import com.example.islands.domain.usecase.component.UpdateComponentStateUseCase
import com.example.islands.presentation.controller.WidgetController
import com.example.islands.presentation.home.viewmodel.HomeViewModel
import com.example.islands.presentation.widget.book.view.BookWidget
import com.example.islands.presentation.widget.book.viewmodel.BooksViewModel
import com.example.islands.presentation.widget.infrastructure.contract.Widget
import com.example.islands.presentation.widget.infrastructure.factory.WidgetsFactory
import com.example.islands.presentation.widget.infrastructure.factory.WidgetsFactoryImpl
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

private val dataModule = module {
    factory { AuthorLocalDataSource() }
    factory { BookLocalDataSource() }
    factory<AuthorRepository> { AuthorRepositoryImpl(get()) }
    factory<BookRepository> { BookRepositoryImpl(get()) }
    single<ComponentRepository> { ComponentRepositoryImpl() }
}

private val domainModule = module {
    factory { FetchAuthorsUseCase(get()) }
    factory { FetchBooksUseCase(get()) }
    factory { RegisterComponentsUseCase(get()) }
    factory { ObserveComponentsUseCase(get()) }
    factory { UpdateComponentStateUseCase(get()) }
}

private val presentationModule = module {
    factory {
        WidgetController(
            observeComponentsUseCase = get(),
            updateComponentStateUseCase = get()
        )
    }
    factory<WidgetsFactory> {
        WidgetsFactoryImpl(
            widgets = getAll()
        )
    }
    viewModel { HomeViewModel(get(), get(), get()) }

    // Book widget
    factory<Widget> { BookWidget() }
    viewModel { BooksViewModel(get()) }
}

fun booksModule(): List<Module> = dataModule + domainModule + presentationModule
