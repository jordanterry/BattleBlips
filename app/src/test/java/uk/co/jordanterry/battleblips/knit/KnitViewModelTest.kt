package uk.co.jordanterry.battleblips.knit

import app.cash.turbine.test
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
internal class KnitViewModelTest {

    private val testViewModel: TestViewModel = TestViewModel()

    @Test
    fun testState() = runTest {
        testViewModel.states.test {
            assertEquals(TestState.Initial, awaitItem())
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun testOtherState() = runTest {
        testViewModel.states.test {
            testViewModel.setState<TestState.Initial> { TestState.Loaded }
            assertEquals(TestState.Initial, awaitItem())
            assertEquals(TestState.Loaded, awaitItem())
            cancelAndConsumeRemainingEvents()
        }
    }
}

sealed interface TestState : KnitState {
    object Initial : TestState
    object Loaded : TestState
}

class TestViewModel : KnitViewModel<TestState>(TestState.Initial)
