package screens.Main.Types

import CustomButton
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import screens.Main.Types.models.TypesEvent
import screens.Main.Types.models.TypesViewState
import tasks.TasksID

@Composable
fun TypesView(state: TypesViewState, eventHandler: (TypesEvent) -> Unit) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column {
            CustomButton(text = "Задание 1", lowerText = "текст", modifier = Modifier.padding(10.dp).size(200.dp, 100.dp)) {eventHandler.invoke(TypesEvent.TypeClicked(TasksID.text))}
            CustomButton(text = "Задание 2", lowerText = "вопросы", modifier = Modifier.padding(10.dp).size(200.dp, 100.dp)) {eventHandler.invoke(TypesEvent.TypeClicked(TasksID.qa))}
            CustomButton(text = "Задание 3", lowerText = "монолог", modifier = Modifier.padding(10.dp).size(200.dp, 100.dp)) {eventHandler.invoke(TypesEvent.TypeClicked(TasksID.monolog))}
        }
    }
}