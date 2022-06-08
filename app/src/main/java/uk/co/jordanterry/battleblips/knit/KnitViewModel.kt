package uk.co.jordanterry.battleblips.knit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.selects.select

abstract class KnitViewModel<State : KnitState>(
    default: State,
    private val reducerDispatcher: CoroutineDispatcher = Dispatchers.Default
) : ViewModel() {

    /**
     * A channel that acts as a queue for reducers.
     *
     * The intention of this queue is to execute the reducers before emitting the resulting [State]
     * from the [ViewModel]s. This ensures that reducers are executed with the most current state.
     */
    private val reducers: Channel<State.() -> State> = Channel(
        capacity = Channel.UNLIMITED
    )
    private val mutableStates: MutableStateFlow<State> = MutableStateFlow(default)

    /**
     * Exposes [State] from the ViewModel that can be collected externally.
     */
    val states: Flow<State> = mutableStates

    internal val state: State
        get() = mutableStates.value

    init {
        reduceReducers()
    }

    private fun reduceReducers() {
        viewModelScope.launch(reducerDispatcher) {
            select<Unit> {
                reducers.onReceive { reducer ->
                    val newState = state.reducer()
                    mutableStates.tryEmit(newState)
                }
            }
        }
    }

    /**
     * Enqueue a reducer that will transform a state.
     *
     * It will not be immediately executed, but instead executed some time in the future
     * on a thread different to the main thread.
     */
    internal inline fun <reified T : State> setState(
        crossinline reducer: T.() -> State
    ) {
        reducers.trySend { transform<T>().reducer() }
    }
}
