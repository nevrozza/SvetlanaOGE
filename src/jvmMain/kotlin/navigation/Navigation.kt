package navigation

import CustomButton
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.alexgladkov.odyssey.compose.extensions.screen
import ru.alexgladkov.odyssey.compose.local.LocalRootController
import ru.alexgladkov.odyssey.compose.navigation.RootComposeBuilder
import screens.Exam.ExamScreen
import screens.Main.Types.TypesScreen
import screens.SplashScreen.SplashScreen
import screens.Main.Variants.VariantsScreen
import tasks.Exam

fun RootComposeBuilder.generateGraph() {

        screen(NavigationTree.Splash.SplashScreen.name) {
            SplashScreen()
        }

        screen(NavigationTree.Main.TypesScreen.name) {
            TypesScreen()
        }

        screen(NavigationTree.Main.VariantsScreen.name) {it as Int
            VariantsScreen(it)
        }

        screen(NavigationTree.Exam.Start.name) {it as Exam

                ExamScreen(it)

        }
}