package screens.Main.Variants

import CustomButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import com.adeo.kviewmodel.compose.observeAsState
import com.adeo.kviewmodel.odyssey.StoredViewModel
import navigation.NavigationTree
import ru.alexgladkov.odyssey.compose.extensions.present
import ru.alexgladkov.odyssey.compose.extensions.push
import ru.alexgladkov.odyssey.compose.local.LocalRootController
import screens.Main.Types.TypesScreen
import screens.Main.Types.models.TypesAction
import screens.Main.Types.models.TypesEvent
import screens.Main.Variants.models.VariantsAction
import tasks.Exam

@Composable
fun VariantsScreen(taskId: Int) {
    val rootController = LocalRootController.current
    println("var")
    StoredViewModel(factory = { VariantsViewModel() }){ viewModel ->
        val state = viewModel.viewStates().observeAsState()
        var action = viewModel.viewActions().observeAsState()
        CustomButton("asd") {
            rootController.popBackStack()
        }
        VariantsView(state = state.value) { event ->
            viewModel.obtainEvent(event)
        }


        when (action.value) {
            is VariantsAction.OpenVariant -> {rootController.push(NavigationTree.Exam.Start.name, Exam(state.value.variant, taskId))}
            else -> {}
        }


    }

}