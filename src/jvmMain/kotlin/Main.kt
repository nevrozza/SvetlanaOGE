import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*
import navigation.generateGraph

import ru.alexgladkov.odyssey.compose.setup.OdysseyConfiguration
import ru.alexgladkov.odyssey.compose.setup.setNavigationContent

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "SvetlanaOGE",
        icon = painterResource("icon.jpeg"),
        state = rememberWindowState(
            width = 1900.dp,
            height = 1080.dp,
            position = WindowPosition.Aligned(Alignment.Center),
            placement = WindowPlacement.Maximized
        )
    ) {
        setNavigationContent(OdysseyConfiguration(), onApplicationFinish = {
            exitApplication()
        }) {
            generateGraph()
        }
    }
}
