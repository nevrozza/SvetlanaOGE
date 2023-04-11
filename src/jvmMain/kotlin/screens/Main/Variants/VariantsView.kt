package screens.Main.Variants

import CustomButton
import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import screens.Main.Variants.models.VariantsEvent
import screens.Main.Variants.models.VariantsViewState
import tasks.ListOfVariants

@Composable
fun VariantsView(state: VariantsViewState, eventHandler: (VariantsEvent) -> Unit) {
    val count = ListOfVariants.all.count()

    AnimatedVisibility(
        state.isInited,
        enter = fadeIn() + expandVertically(expandFrom = Alignment.Bottom),
        exit = fadeOut() + shrinkVertically(shrinkTowards = Alignment.Bottom)
    ) {
        IconButton(onClick = { eventHandler.invoke(VariantsEvent.BackPressed) }) {
            Icon(Icons.Filled.KeyboardArrowLeft, contentDescription = null, modifier = Modifier.size(40.dp))
        }
    }
    
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.padding(horizontal = 200.dp),
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            contentPadding = PaddingValues(20.dp)
        ) {
            items(count){
                CustomButton(text = "Вариант ${it+1}", modifier = Modifier.size(200.dp, 100.dp)) {eventHandler.invoke(VariantsEvent.VariantClicked(ListOfVariants.all[it]))}
            }
        }
    }
}