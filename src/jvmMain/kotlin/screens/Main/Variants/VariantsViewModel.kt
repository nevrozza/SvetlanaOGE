package screens.Main.Variants

import com.adeo.kviewmodel.BaseSharedViewModel
import screens.Main.Variants.models.VariantsAction
import screens.Main.Variants.models.VariantsEvent
import screens.Main.Variants.models.VariantsViewState

class VariantsViewModel: BaseSharedViewModel<VariantsViewState, VariantsAction, VariantsEvent>(
    initialState = VariantsViewState(variant = listOf(""))
) {
    override fun obtainEvent(viewEvent: VariantsEvent) {
        when (viewEvent) {
            is VariantsEvent.VariantClicked -> openVariant(viewEvent.variant)
            is VariantsEvent.ActionInvoked -> viewAction = null
            is VariantsEvent.BackPressed -> backPressed()
            is VariantsEvent.ViewInited -> viewInited()
        }
    }
    private fun viewInited() {
        viewState = viewState.copy(isInited = true)
    }
    private fun backPressed() {
        viewAction = VariantsAction.Back
        viewState = viewState.copy(isInited = false)
    }
    private fun openVariant(variant: List<String>) {
        viewState = viewState.copy(variant = variant)
        viewAction = VariantsAction.OpenVariant
    }

}