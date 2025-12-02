package edu.ucne.morenofootball.ui.presentation.productoDetalles

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun ProductoDetalleScreen(
    viewModel: ProductoDetalleViewModel = hiltViewModel(),
    navigateBack: () -> Unit,
    navigateToCart: () -> Unit,
) {
    val state = viewModel.state.collectAsStateWithLifecycle()

    ProductoDetalleBody(
        state = state.value,
        onEvent = viewModel::onEvent
    )
}

@Composable
fun ProductoDetalleBody(
    state: ProductoDetalleUiState,
    onEvent: (ProductoDetalleUiEvent) -> Unit,
) {
    TODO("Not yet implemented")
}