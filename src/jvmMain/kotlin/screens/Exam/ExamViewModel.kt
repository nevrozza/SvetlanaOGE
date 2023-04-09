package screens.Exam

import com.adeo.kviewmodel.BaseSharedViewModel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import screens.Exam.models.ExamAction
import screens.Exam.models.ExamEvent
import screens.Exam.models.ExamViewState
import tasks.TasksID
import kotlin.time.Duration

class ExamViewModel(tasks: List<String>, taskId: Int, title: String, taskText: String, taskNumber: Int): BaseSharedViewModel<ExamViewState, ExamAction, ExamEvent>(
    initialState = ExamViewState(taskId = taskId, title = title, taskText = taskText, seconds = 1, taskNumber = taskNumber, tasks = tasks)
) {
    override fun obtainEvent(viewEvent: ExamEvent) {
        when(viewEvent){
            is ExamEvent.TimerStarted -> startTimer(viewEvent.secondsInit)
            is ExamEvent.Skip -> skipTask()
            else -> {}
        }
    }

    private fun skipTask() {
        viewState = viewState.copy(seconds = 0)
    }

    private fun startTimer(secondsInit: Int) {
        var seconds = secondsInit
        println("sad")
        viewModelScope.launch {
            while(viewState.seconds != 0){
                viewState = viewState.copy(seconds = seconds)
                delay(100)
                seconds--
            }


            if(secondsInit == 90) {
                obtainEvent(ExamEvent.TimerStarted(125))
                viewState = viewState.copy(timerText = "Answer")
            }
            else {
                viewState = viewState.copy(taskId = viewState.taskId+1)
                if(viewState.taskId in TasksID.qa until TasksID.monolog) {
                    obtainEvent(ExamEvent.TimerStarted(45))
                } else {
                    obtainEvent(ExamEvent.TimerStarted(90))
                }
                viewState = viewState.copy(taskText = viewState.tasks[viewState.taskId])
            }



        }
    }
}