package screens.Main.Variants.models

sealed class VariantsEvent {
    data class VariantClicked(val variant: List<String>) : VariantsEvent()
    object ActionInvoked: VariantsEvent()
    object BackPressed: VariantsEvent()
}
