import androidx.compose.material.Button
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import tasks.CColors

@Composable
fun CustomButton(text: String, lowerText: String = "", modifier: Modifier = Modifier, onClick: () -> Unit){
    Button(onClick = {
        onClick.invoke()
    }, colors = ButtonDefaults.buttonColors(backgroundColor = CColors.blueColor), modifier = modifier) {
        Text(text = buildAnnotatedString {
            withStyle(style = SpanStyle(fontSize = 14.sp)){append(text)}
            if(lowerText != "") withStyle(style = SpanStyle(fontSize = 12.sp)){append("\n"+lowerText)}
        }, textAlign = TextAlign.Center, color = Color.White)
    }
}