package uk.co.jordanterry.battleblips.game.setup

import uk.co.jordanterry.battleblips.viewmodels.AbsViewModel

class SetupViewModel : AbsViewModel<SetupViewModel.UiModel>(
    default = UiModel.Loading
) {

    sealed interface UiModel {
        object Loading : UiModel
    }
}