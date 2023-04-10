package screens.Exam.models

sealed class ExamEvent {
    data class TimerStarted(val secondsInit: Int): ExamEvent()
    object Skip: ExamEvent()
    object StopClicked: ExamEvent()
    object BackClicked: ExamEvent()
    object FullQAClicked: ExamEvent()
    object HomeClicked: ExamEvent()
}
