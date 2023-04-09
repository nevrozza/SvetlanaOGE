package screens.Main.Variants

import CustomButton
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import screens.Main.Variants.models.VariantsEvent
import screens.Main.Variants.models.VariantsViewState
import tasks.ListOfVariants

@Composable
fun VariantsView(state: VariantsViewState, eventHandler: (VariantsEvent) -> Unit) {
    val count = tasks.ListOfVariants.all.count()
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        LazyVerticalGrid(columns = GridCells.Fixed(2)) {
            items(count){
                CustomButton(text = "Вариант ${it+1}") {eventHandler.invoke(VariantsEvent.VariantClicked(ListOfVariants.all[it]))}
            }
        }
    }
}