import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import navigation.generateGraph

import ru.alexgladkov.odyssey.compose.local.LocalRootController
import ru.alexgladkov.odyssey.compose.setup.OdysseyConfiguration
import ru.alexgladkov.odyssey.compose.setup.setNavigationContent

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "SvetlanaOGE",
        state = rememberWindowState(
            width = 1024.dp,
            height = 800.dp,
            position = WindowPosition.Aligned(Alignment.Center)
        )
    ) {
        setNavigationContent(OdysseyConfiguration(), onApplicationFinish = {
            exitApplication()
        }) {
            generateGraph()
        }
    }
}
