package tasks

import androidx.compose.ui.graphics.Color

data class Exam(val variant: List<String>, val taskId: Int)

object CColors {
    val blueColor = Color(37, 110, 173)
}

object Timings {
    const val preparation = 90
    const val answering = 125
    const val questions = 45
}

object Titles {
    const val text = "Task 1. You are going to read the text aloud. You have 1.5 minutes to read text " +
            "silently, and then be ready to read it aloud. Remember that you will not have " +
            "more than 2 minutes for reading aloud."
    const val qa = "Task 2. You are going to take part in a telephone survey. You have to answer six " +
            "questions. Give full answers to the questions. Remember that you have 40 " +
            "seconds to answer each question."
    const val monolog = "Task 3. You are going to give a talk about travelling. You will have to start in 1.5 " +
            "minutes and speak for not more than 2 minutes (10-12 sentences)."
}

object TasksID {
    const val text = 0
    const val qa = 1
    const val monolog = 7
}