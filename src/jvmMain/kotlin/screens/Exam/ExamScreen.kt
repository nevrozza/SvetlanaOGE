package screens.Exam

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import com.adeo.kviewmodel.compose.observeAsState

import com.adeo.kviewmodel.odyssey.StoredViewModel
import ru.alexgladkov.odyssey.compose.local.LocalRootController
import screens.Exam.models.ExamEvent
import tasks.Exam
import tasks.TasksID
import tasks.Titles

@Composable
fun ExamScreen(exam: Exam) {
    val rootController = LocalRootController.current


    val initialTaskId = remember { exam.taskId }
    val initialTaskText = remember { exam.variant[initialTaskId] }


    val initialTitle: String
    val initialTaskNumber: Int

    when (exam.taskId) {
        TasksID.text -> {
            initialTitle = remember { Titles.text }; initialTaskNumber = remember { 1 }
        }

        TasksID.monolog -> {
            initialTitle = remember { Titles.monolog }; initialTaskNumber = remember { 3 }
        }

        else -> {
            initialTitle = remember { Titles.qa }; initialTaskNumber = remember { 2 }
        }
    }




    StoredViewModel(factory = {
        ExamViewModel(
            exam.variant,
            initialTaskId,
            initialTitle,
            initialTaskText,
            initialTaskNumber
        )
    }) { viewModel ->
        val state = viewModel.viewStates().observeAsState()
        val action = viewModel.viewActions().observeAsState()


        ExamView(state = state.value) { event ->

            viewModel.obtainEvent(event)
        }


        when (action.value) {

            else -> {}
        }

        LaunchedEffect(Unit) {
            viewModel.obtainEvent(ExamEvent.TimerStarted(state.value.seconds))
        }
    }
}
