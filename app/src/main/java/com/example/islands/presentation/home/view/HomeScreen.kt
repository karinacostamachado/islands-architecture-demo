package com.example.islands.presentation.home.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.example.islands.presentation.home.action.HomeAction
import com.example.islands.presentation.home.viewmodel.HomeViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = koinViewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.handleAction(HomeAction.OnLaunch)
    }
    Column(modifier = modifier.fillMaxSize()) {
        val widgets = viewModel.widgets

        widgets.forEach { widget ->
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                widget.Render(
                    state = viewModel.getWidgetState(widget.identifier),
                    updateState = { newState ->
                        viewModel.handleAction(
                            HomeAction.UpdateWidgetState(widget.identifier, newState)
                        )
                    }
                )
            }
        }
    }
}
