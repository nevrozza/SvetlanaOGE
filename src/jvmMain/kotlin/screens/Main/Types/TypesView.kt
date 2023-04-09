package screens.Main.Types

import CustomButton
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import screens.Main.Types.models.TypesEvent
import screens.Main.Types.models.TypesViewState
import tasks.TasksID

@Composable
fun TypesView(state: TypesViewState, eventHandler: (TypesEvent) -> Unit) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column {
            CustomButton(text = "Задание 1", lowerText = "текст") {eventHandler.invoke(TypesEvent.TypeClicked(TasksID.text))}
            CustomButton(text = "Задание 2", lowerText = "вопросы") {eventHandler.invoke(TypesEvent.TypeClicked(TasksID.qa))}
            CustomButton(text = "Задание 3", lowerText = "монолог") {eventHandler.invoke(TypesEvent.TypeClicked(TasksID.monolog))}
        }
    }
}