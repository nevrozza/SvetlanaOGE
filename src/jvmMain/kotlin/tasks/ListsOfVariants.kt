package tasks

//data class Variant(val text: String, val qa1: String, val qa2: String, val qa3: String, val qa4: String, val qa5: String, val qa6: String, val monolog: String)

object ListOfVariants {
//    val v1 = Variant(
//        text = Tasks.V1.text,
//        qa1 = Tasks.V1.qa1,
//        qa2 = Tasks.V1.qa2,
//        qa3 = Tasks.V1.qa3,
//        qa4 = Tasks.V1.qa4,
//        qa5 = Tasks.V1.qa5,
//        qa6 = Tasks.V1.qa6,
//        monolog = Tasks.V1.monolog,
//    )
    private val v1 = listOf(Tasks.V1.text, Tasks.V1.qa1, Tasks.V1.qa2, Tasks.V1.qa3, Tasks.V1.qa4, Tasks.V1.qa5, Tasks.V1.qa6, Tasks.V1.monolog)

    val all = listOf(v1)
}