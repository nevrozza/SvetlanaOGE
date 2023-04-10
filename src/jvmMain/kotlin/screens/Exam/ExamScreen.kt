package screens.Exam

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import com.adeo.kviewmodel.compose.observeAsState

import com.adeo.kviewmodel.odyssey.StoredViewModel
import navigation.NavigationTree
import ru.alexgladkov.odyssey.compose.extensions.deepLink
import ru.alexgladkov.odyssey.compose.extensions.present
import ru.alexgladkov.odyssey.compose.extensions.push
import ru.alexgladkov.odyssey.compose.extensions.pushDeepLink
import ru.alexgladkov.odyssey.compose.local.LocalRootController
import ru.alexgladkov.odyssey.core.LaunchFlag
import screens.Exam.models.ExamAction
import screens.Exam.models.ExamEvent
import screens.Exam.models.ExamViewState
import tasks.Exam
import tasks.TasksID
import tasks.Timings
import tasks.Titles

@Composable
fun ExamScreen(exam: Exam) {
    val rootController = LocalRootController.current

    val init = changeExam(exam)
    StoredViewModel(factory = {
        ExamViewModel(
            init.tasks,
            init.taskId,
            init.title,
            init.taskText,
            init.taskNumber,
            init.seconds
        )
    }) { viewModel ->
        val state = viewModel.viewStates().observeAsState()
        val action = viewModel.viewActions().observeAsState()


        ExamView(state = state.value) { event ->

            viewModel.obtainEvent(event)
        }


        when (action.value) {
            ExamAction.GoHome -> rootController.present(NavigationTree.Splash.SplashScreen.name, launchFlag = LaunchFlag.SingleNewTask)
            else -> {}
        }

        LaunchedEffect(Unit) {
            viewModel.obtainEvent(ExamEvent.TimerStarted(state.value.seconds))
        }
    }
}
