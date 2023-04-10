package screens.Main.Variants

import CustomButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import com.adeo.kviewmodel.compose.observeAsState
import com.adeo.kviewmodel.odyssey.StoredViewModel
import navigation.NavigationTree
import ru.alexgladkov.odyssey.compose.extensions.present
import ru.alexgladkov.odyssey.compose.extensions.push
import ru.alexgladkov.odyssey.compose.local.LocalRootController
import screens.Exam.models.ExamEvent
import screens.Main.Types.TypesScreen
import screens.Main.Types.models.TypesAction
import screens.Main.Types.models.TypesEvent
import screens.Main.Variants.models.VariantsAction
import screens.Main.Variants.models.VariantsEvent
import tasks.Exam

@Composable
fun VariantsScreen(taskId: Int) {
    val rootController = LocalRootController.current

    StoredViewModel(factory = { VariantsViewModel() }){ viewModel ->
        val state = viewModel.viewStates().observeAsState()
        val action = viewModel.viewActions().observeAsState()
        VariantsView(state = state.value) { event ->
            viewModel.obtainEvent(event)
        }


        when (action.value) {
            is VariantsAction.OpenVariant -> {rootController.push(NavigationTree.Exam.Start.name, Exam(state.value.variant, taskId)); viewModel.obtainEvent(VariantsEvent.ActionInvoked)}
            is VariantsAction.Back -> {rootController.popBackStack()}
            null -> {}
        }


    }

}