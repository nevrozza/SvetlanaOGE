package screens.Exam

import com.adeo.kviewmodel.BaseSharedViewModel
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.launch
import screens.Exam.models.ExamAction
import screens.Exam.models.ExamEvent
import screens.Exam.models.ExamViewState
import tasks.Exam
import tasks.TasksID
import tasks.Timings
import tasks.Titles

class ExamViewModel(tasks: List<String>, taskId: Int, title: String, taskText: String, taskNumber: Int, seconds: Int): BaseSharedViewModel<ExamViewState, ExamAction, ExamEvent>(
    initialState = ExamViewState(taskId = taskId, title = title, taskText = taskText, seconds = seconds, taskNumber = taskNumber, tasks = tasks, initialSeconds = seconds)
) {
    override fun obtainEvent(viewEvent: ExamEvent) {
        when(viewEvent){
            is ExamEvent.TimerStarted -> startTimer(viewEvent.secondsInit)
            is ExamEvent.Skip -> skipTask()
            is ExamEvent.StopClicked -> stop()
            is ExamEvent.BackClicked -> back()
            is ExamEvent.FullQAClicked -> fullQaClicked()
            is ExamEvent.HomeClicked -> homeClicked()
        }
    }

    private fun homeClicked() {
        viewAction = ExamAction.GoHome
    }
    private fun skipTask() {
        viewState = viewState.copy(seconds = 0)
    }

    private fun startTimer(secondsInit: Int) {

        var seconds = secondsInit
        viewState = viewState.copy(seconds = seconds)

        viewModelScope.launch {
            while(viewState.seconds != 0){
                if(viewState.isBacked) {
                    seconds = viewState.initialSeconds
                    viewState = viewState.copy(isBacked = false)
                }
                if(!viewState.isPause) {
                    viewState = viewState.copy(seconds = seconds)
                    seconds--
                }
                delay(1000)
            }

            if(secondsInit == Timings.preparation) {

                obtainEvent(ExamEvent.TimerStarted(Timings.answering))
                viewState = viewState.copy(timerText = "Answer")
            }
            else {
                if(viewState.taskId != TasksID.monolog) {
                    viewState = viewState.copy(taskId = viewState.taskId+1)
                } else {
                    if(!viewState.isPause) {
                        obtainEvent(ExamEvent.StopClicked)
                    }
                }

                if(viewState.taskId in TasksID.qa until TasksID.monolog) {
                    obtainEvent(ExamEvent.TimerStarted(Timings.questions))
                } else {
                    obtainEvent(ExamEvent.TimerStarted(Timings.preparation))
                }

                viewState = changeExam(Exam(viewState.tasks, viewState.taskId), isPause = viewState.isPause, isFullQA = viewState.isFullQA)
            }
        }
    }
    private fun stop() {
        viewState= viewState.copy(isPause = !viewState.isPause)
    }
    private fun back() {
        viewState = changeExam(Exam(viewState.tasks, viewState.taskId-1), isPause = viewState.isPause, isFullQA = viewState.isFullQA, isBacked = true)
    }

    private fun fullQaClicked() {
        viewState = viewState.copy(isFullQA = !viewState.isFullQA)
    }
}



fun changeExam(exam: Exam, timerText: String = "Preparation", isPause: Boolean = false, isFullQA: Boolean = true, isBacked: Boolean = false): ExamViewState {
    val initialTaskId = exam.taskId
    val initialTaskText = exam.variant[initialTaskId]


    val initialTitle: String
    val initialTaskNumber: Int
    var initialSeconds = Timings.preparation

    when (exam.taskId) {
        TasksID.text -> {
            initialTitle = Titles.text; initialTaskNumber = 1
        }

        TasksID.monolog -> {
            initialTitle = Titles.monolog; initialTaskNumber = 3
        }

        else -> {
            initialTitle = Titles.qa; initialTaskNumber = 2; initialSeconds = Timings.questions
        }
    }
    return ExamViewState(exam.variant, exam.taskId, initialTitle, initialTaskText, initialSeconds, initialSeconds, initialTaskNumber, timerText, isPause, isFullQA, isBacked)
}