package screens.Exam.models

data class ExamViewState (
    val tasks: List<String>,
    val taskId: Int,
    val title: String,
    val taskText: String,
    val seconds: Int,
    val taskNumber: Int,
    val timerText: String = "Preparation",
)