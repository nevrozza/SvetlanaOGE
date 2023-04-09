package screens.SplashScreen

import androidx.compose.runtime.Composable
import navigation.NavigationTree
import ru.alexgladkov.odyssey.compose.extensions.present
import ru.alexgladkov.odyssey.compose.local.LocalRootController
import java.awt.SplashScreen

@Composable
fun SplashScreen() {
    val rootController = LocalRootController.current
    rootController.present(NavigationTree.Main.TypesScreen.name)
}