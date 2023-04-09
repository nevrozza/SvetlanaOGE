package screens.Exam

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import screens.Exam.models.ExamEvent
import screens.Exam.models.ExamViewState
import tasks.CColors

@Composable
fun ExamView(state: ExamViewState, eventHandler: (ExamEvent) -> Unit) {
    Column(modifier = Modifier.fillMaxSize()) {

        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Card(modifier = Modifier.size(48.dp), shape = CircleShape, colors = CardDefaults.cardColors(containerColor = CColors.blueColor)) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = state.taskNumber.toString(), color = Color.White, fontSize = 27.sp)
                }
            }
            Spacer(modifier = Modifier.width(15.dp))
            Text(state.title, fontSize = 27.sp)
        }

        Divider(modifier = Modifier.padding(top = 10.dp, bottom = 10.dp).fillMaxWidth().height(2.dp), color = Color.LightGray)

        Text(state.taskText, fontSize = 40.sp)


    }
}