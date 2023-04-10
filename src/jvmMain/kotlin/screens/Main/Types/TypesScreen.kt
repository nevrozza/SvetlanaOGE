package screens.Main.Types

import CustomButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import com.adeo.kviewmodel.compose.observeAsState
import com.adeo.kviewmodel.odyssey.StoredViewModel
import navigation.NavigationTree
import ru.alexgladkov.odyssey.compose.extensions.present
import ru.alexgladkov.odyssey.compose.extensions.push
import ru.alexgladkov.odyssey.compose.local.LocalRootController
import screens.Main.Types.models.TypesAction
import screens.Main.Types.models.TypesEvent
import screens.Main.Types.models.TypesViewState

@Composable
fun TypesScreen() {
    val rootController = LocalRootController.current

    StoredViewModel(factory = { TypesViewModel() }) { viewModel ->
        val state = viewModel.viewStates().observeAsState()
        val action = viewModel.viewActions().observeAsState()



        TypesView(state = state.value) { event ->
            viewModel.obtainEvent(event)
        }



        when (action.value) {
            is TypesAction.TypesOpen -> { rootController.push(NavigationTree.Main.VariantsScreen.name, state.value.id); viewModel.obtainEvent(TypesEvent.ActionInvoked) }

            else -> {}
        }
    





    }
}

