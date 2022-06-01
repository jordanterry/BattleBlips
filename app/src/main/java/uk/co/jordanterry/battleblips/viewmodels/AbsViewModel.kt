package uk.co.jordanterry.battleblips.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class AbsViewModel<UiModel : Any>(
    default: UiModel
) : ViewModel() {
    protected val _uiModel: MutableLiveData<UiModel> = MutableLiveData<UiModel>(default)
    val uiModel: LiveData<UiModel> = _uiModel
}