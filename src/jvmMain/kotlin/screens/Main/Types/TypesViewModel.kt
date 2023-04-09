package screens.Main.Types

import com.adeo.kviewmodel.BaseSharedViewModel
import screens.Main.Types.models.TypesAction
import screens.Main.Types.models.TypesEvent
import screens.Main.Types.models.TypesViewState

class TypesViewModel: BaseSharedViewModel<TypesViewState, TypesAction, TypesEvent>(
    initialState = TypesViewState(99)
) {
    override fun obtainEvent(viewEvent: TypesEvent) {
        when (viewEvent) {
            is TypesEvent.TypeClicked -> openType(viewEvent.id)
            TypesEvent.ActionInvoked -> viewAction = null
        }
    }

    private fun openType(id: Int) {
        viewState = viewState.copy(id = id)
        viewAction = TypesAction.TypesOpen

    }

}