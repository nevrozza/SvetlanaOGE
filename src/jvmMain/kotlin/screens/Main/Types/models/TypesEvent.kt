package screens.Main.Types.models

sealed class TypesEvent {
    data class TypeClicked(val id: Int): TypesEvent()
    object ActionInvoked: TypesEvent()
}