package screens.Main.Types

import androidx.compose.runtime.Composable
import com.adeo.kviewmodel.compose.observeAsState
import com.adeo.kviewmodel.odyssey.StoredViewModel
import navigation.NavigationTree
import ru.alexgladkov.odyssey.compose.local.LocalRootController
import ru.alexgladkov.odyssey.core.animations.AnimationType
import screens.Main.Types.models.TypesAction
import screens.Main.Types.models.TypesEvent
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
            is TypesAction.TypesOpen -> {
                rootController.launch(
                    screen = NavigationTree.Main.VariantsScreen.name,
                    params = state.value.id,
                    animationType = AnimationType.Fade(300)); viewModel.obtainEvent(TypesEvent.ActionInvoked) }

            else -> {}
        }
    





    }
}

