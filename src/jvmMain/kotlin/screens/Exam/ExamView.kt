package screens.Exam

import CustomButton
import androidx.compose.animation.core.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import screens.Exam.models.ExamEvent
import screens.Exam.models.ExamViewState
import tasks.CColors
import tasks.TasksID

@Composable
fun ExamView(state: ExamViewState, eventHandler: (ExamEvent) -> Unit) {
    val percent = state.initialSeconds / 100f
    val animatedProgress = animateFloatAsState(
        (state.initialSeconds / percent - state.seconds / percent) / 100,
        animationSpec = tween(1000, easing = LinearEasing)
    )
    val minutesF = state.seconds / 60
    val secondsF = state.seconds - minutesF * 60
    var text: String = remember { "" }

    when (state.taskId) {
        TasksID.text -> text = state.taskText
        in TasksID.qa until TasksID.monolog -> {
            if (!state.isFullQA) {
                for (i in TasksID.qa..state.taskId) {
                    text += "${i}. ${state.tasks[i]}\n"
                }
            } else if (state.seconds > 38) text = state.taskText else text = ""
        }

        TasksID.monolog -> {
            text =
                "\uD835\uDE4D\uD835\uDE5A\uD835\uDE62\uD835\uDE5A\uD835\uDE62\uD835\uDE57\uD835\uDE5A\uD835\uDE67 \uD835\uDE69\uD835\uDE64 \uD835\uDE68\uD835\uDE56\uD835\uDE6E:\n"; for (i in 0..3) {
                text += "   · ${state.taskText.split("\n")[i]}\n"
            }; text += "\uD835\uDE54\uD835\uDE64\uD835\uDE6A \uD835\uDE5D\uD835\uDE56\uD835\uDE6B\uD835\uDE5A \uD835\uDE69\uD835\uDE64 \uD835\uDE69\uD835\uDE56\uD835\uDE61\uD835\uDE60 \uD835\uDE58\uD835\uDE64\uD835\uDE63\uD835\uDE69\uD835\uDE5E\uD835\uDE63\uD835\uDE6A\uD835\uDE64\uD835\uDE6A\uD835\uDE68\uD835\uDE61\uD835\uDE6E."
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Card(
                    modifier = Modifier.size(48.dp),
                    shape = CircleShape,
                    colors = CardDefaults.cardColors(containerColor = CColors.blueColor)
                ) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(text = state.taskNumber.toString(), color = Color.White, fontSize = 27.sp)
                    }
                }
                Spacer(modifier = Modifier.width(15.dp))
                Text(state.title, fontSize = 27.sp)
            }

            Divider(
                modifier = Modifier.padding(top = 10.dp, bottom = 10.dp).fillMaxWidth().height(2.dp),
                color = Color.LightGray
            )

        }
        val checkUsl = state.isFullQA && state.taskId in TasksID.qa until TasksID.monolog
        Box(
            modifier = if (checkUsl) Modifier.height(700.dp).fillMaxWidth() else Modifier.padding(20.dp),
            contentAlignment = if (checkUsl) Alignment.Center else Alignment.TopStart
        ) {
            Text(text, fontSize = 40.sp)
        }

        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            if(state.taskId != TasksID.text) {
                IconButton(onClick = { eventHandler.invoke(ExamEvent.BackClicked) }) {
                    Icon(Icons.Default.KeyboardArrowLeft, contentDescription = null)
                }
            }

            IconButton(onClick = { eventHandler.invoke(ExamEvent.HomeClicked) }) {
                Icon(Icons.Default.Home, contentDescription = null)
            }

            Row(
                modifier = Modifier.fillMaxWidth().height(50.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Box(contentAlignment = Alignment.Center, modifier = Modifier.size(40.dp)) {
                    if (state.taskId in TasksID.qa until TasksID.monolog) {
                        if (!state.isFullQA) {
                            Text(
                                "/",
                                modifier = Modifier.padding(bottom = 6.dp),
                                fontSize = 20.sp,
                                color = Color.Black
                            )
                        }
                        IconButton(onClick = { eventHandler.invoke(ExamEvent.FullQAClicked) }) {
                            Icon(Icons.Default.Search, contentDescription = null)
                        }
                    }
                }

                Text(
                    state.timerText,
                    color = Color.Black,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(horizontal = 6.dp).width(110.dp),
                    textAlign = TextAlign.Center
                )
                Row(modifier = Modifier.padding(top = 2.dp).clip(RoundedCornerShape(20)).clickable() { eventHandler.invoke(ExamEvent.StopClicked) }) {
                    LinearProgressIndicator(
                        progress = animatedProgress.value,
                        modifier = Modifier
                            .height(25.dp)
                            .padding(horizontal = 6.dp)
                            .width(800.dp)
                            .clip(AbsoluteRoundedCornerShape(20)),
                        color = CColors.blueColor
                    )

                    Text(
                        "0$minutesF:${if (secondsF == 0) "00" else if (secondsF < 10) "0$secondsF" else secondsF}",
                        modifier = Modifier.width(60.dp),
                        color = Color.Black,
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center
                    )
                }
                if(state.taskId != TasksID.monolog) {
                    IconButton(onClick = { eventHandler.invoke(ExamEvent.Skip) }) {
                        Icon(Icons.Default.KeyboardArrowRight, contentDescription = null)
                    }
                }
            }



        }


    }
}