package screens.Exam

import com.adeo.kviewmodel.BaseSharedViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import screens.Exam.models.ExamAction
import screens.Exam.models.ExamEvent
import screens.Exam.models.ExamViewState
import tasks.Exam
import tasks.TasksID
import tasks.Timings
import tasks.Titles
import javax.sound.sampled.AudioSystem

class ExamViewModel(tasks: List<String>, taskId: Int, title: String, taskText: String, taskNumber: Int, seconds: Int) :
    BaseSharedViewModel<ExamViewState, ExamAction, ExamEvent>(
        initialState = ExamViewState(
            taskId = taskId,
            title = title,
            taskText = taskText,
            seconds = seconds,
            taskNumber = taskNumber,
            tasks = tasks,
            initialSeconds = seconds,
            timerText = if (taskId in TasksID.qa until TasksID.monolog) "Listening" else "Preparation"
        )
    ) {
    override fun obtainEvent(viewEvent: ExamEvent) {
        when (viewEvent) {
            is ExamEvent.TimerStarted -> startTimer(viewEvent.secondsInit)
            is ExamEvent.Skip -> skipTask()
            is ExamEvent.StopClicked -> stop()
            is ExamEvent.BackClicked -> back()
            is ExamEvent.FullQAClicked -> fullQaClicked()
            is ExamEvent.HomeClicked -> homeClicked()
            is ExamEvent.SoundClicked -> soundClicked()
        }
    }

    private fun soundClicked(){
        viewState = viewState.copy(isSound = !viewState.isSound)
    }

    private fun homeClicked() {
        viewAction = ExamAction.GoHome
    }

    private fun skipTask() {
        viewState = viewState.copy(seconds = 0)
    }

    private fun startTimer(secondsInit: Int) {
        var newSecondsInit = secondsInit
        var seconds = secondsInit
        viewState = viewState.copy(seconds = seconds)

        val clip = AudioSystem.getClip()
        val jinc = AudioSystem.getAudioInputStream(Thread.currentThread().contextClassLoader.getResource("jinc.wav"))
        val ssp = AudioSystem.getAudioInputStream(Thread.currentThread().contextClassLoader.getResource("ssp.wav"))

        viewModelScope.launch {
            while (viewState.seconds != 0) {
                if (viewState.isBacked) {
                    seconds = viewState.initialSeconds
                    newSecondsInit = viewState.initialSeconds
                    viewState = viewState.copy(isBacked = false)
                }
                if (!viewState.isPause) {
                    viewState = viewState.copy(seconds = seconds)
                    seconds--
                    if (viewState.seconds == 40 && viewState.taskId in TasksID.qa until TasksID.monolog) {
                        viewState = viewState.copy(timerText = "Answering")

                        if(viewState.isSound) {
                            clip.open(jinc)
                            clip.start()
                        }
                    }
                }
                delay(1000)
            }

            if (newSecondsInit == Timings.preparation) {
                obtainEvent(ExamEvent.TimerStarted(Timings.answering))
                viewState = viewState.copy(timerText = "Answering")
                if(viewState.isSound) {
                    clip.open(ssp)
                    clip.start()
                }

            } else {
                if (viewState.taskId != TasksID.monolog) {
                    viewState = viewState.copy(taskId = viewState.taskId + 1)
                } else {
                    if (!viewState.isPause) {
                        obtainEvent(ExamEvent.StopClicked)
                    }
                }

                viewState = if (viewState.taskId in TasksID.qa until TasksID.monolog) {
                    obtainEvent(ExamEvent.TimerStarted(Timings.questions))
                    viewState.copy(timerText = "Listening")
                } else {
                    obtainEvent(ExamEvent.TimerStarted(Timings.preparation))
                    viewState.copy(timerText = "Preparation")
                }

                viewState = changeExam(
                    Exam(viewState.tasks, viewState.taskId),
                    isPause = viewState.isPause,
                    isFullQA = viewState.isFullQA,
                    timerText = viewState.timerText
                )
            }
        }
    }

    private fun stop() {
        viewState = viewState.copy(isPause = !viewState.isPause)
    }

    private fun back() {
        viewState = changeExam(
            Exam(viewState.tasks, viewState.taskId - 1),
            isPause = viewState.isPause,
            isFullQA = viewState.isFullQA,
            isBacked = true,
            timerText = if (viewState.taskId - 1 in TasksID.qa until TasksID.monolog) "Listening" else "Preparation"
        )
    }

    private fun fullQaClicked() {
        viewState = viewState.copy(isFullQA = !viewState.isFullQA)
    }
}


fun changeExam(
    exam: Exam,
    timerText: String = "Preparation",
    isPause: Boolean = false,
    isFullQA: Boolean = true,
    isBacked: Boolean = false
): ExamViewState {
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
    return ExamViewState(
        exam.variant,
        exam.taskId,
        initialTitle,
        initialTaskText,
        initialSeconds,
        initialSeconds,
        initialTaskNumber,
        timerText,
        isPause,
        isFullQA,
        isBacked
    )
}