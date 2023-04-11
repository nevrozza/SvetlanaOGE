package screens.Exam.models



data class ExamViewState (
    val tasks: List<String>,
    val taskId: Int,
    val title: String,
    val taskText: String,
    val initialSeconds: Int,
    val seconds: Int,
    val taskNumber: Int,
    val timerText: String = "Preparation",
    val isPause: Boolean = false,
    val isFullQA: Boolean = true,
    val isBacked: Boolean = false,
    val isSound: Boolean = true
)