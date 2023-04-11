package screens.Main.Variants

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.adeo.kviewmodel.compose.observeAsState
import com.adeo.kviewmodel.odyssey.StoredViewModel
import navigation.NavigationTree
import ru.alexgladkov.odyssey.compose.local.LocalRootController
import ru.alexgladkov.odyssey.core.animations.AnimationType
import screens.Main.Variants.models.VariantsAction
import screens.Main.Variants.models.VariantsEvent
import tasks.Exam

@Composable
fun VariantsScreen(taskId: Int) {
    val rootController = LocalRootController.current

    StoredViewModel(factory = { VariantsViewModel() }) { viewModel ->
        val state = viewModel.viewStates().observeAsState()
        val action = viewModel.viewActions().observeAsState()
        VariantsView(state = state.value) { event ->
            viewModel.obtainEvent(event)
        }


        when (action.value) {
            is VariantsAction.OpenVariant -> {
                rootController.launch(
                    screen = NavigationTree.Exam.Start.name,
                    params = Exam(state.value.variant, taskId),
                    animationType = AnimationType.Fade(300)
                ); viewModel.obtainEvent(VariantsEvent.ActionInvoked)
            }

            is VariantsAction.Back -> {
                rootController.popBackStack()
            }

            null -> {}
        }

        LaunchedEffect(Unit) {
            viewModel.obtainEvent(VariantsEvent.ViewInited)
        }


    }

}