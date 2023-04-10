package screens.Main.Variants.models

sealed class VariantsAction {
    object OpenVariant: VariantsAction()
    object Back: VariantsAction()
}
