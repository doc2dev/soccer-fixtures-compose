package ke.eston.soccerfixtures.ui.viewmodel

import ke.eston.soccerfixtures.domain.model.Fixture
import ke.eston.soccerfixtures.domain.repository.Error
import ke.eston.soccerfixtures.domain.repository.OperationOutcome
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class LiveScoreViewModelTest {
    @get:Rule
    val dispatcherRule = TestDispatcherRule()

    @Test
    fun `should emit default state of not loading, no error`() = runTest {
        val viewModel = LiveScoreViewModel(
            object: FakeLiveScoreRepository() {},
            UnconfinedTestDispatcher()
        )

        val firstState = viewModel.fixtureUiState.first()

        assert(firstState is FixtureState.FixturesUnavailable)
        assert(!firstState.isLoading)
        assert(firstState.error == null)
    }

    @Test
    fun `should emit loading state when starting API call`() = runTest {
        val repository = object : FakeLiveScoreRepository() {
            override suspend fun getFixtures(): OperationOutcome<List<Fixture>> {
                delay(100)
                return OperationOutcome.Success(listOf())
            }
        }
        val viewModel = LiveScoreViewModel(
            repository,
            UnconfinedTestDispatcher()
        )

        viewModel.getFixtures()
        val firstState = viewModel.fixtureUiState.drop(1).first()

        assert(firstState is FixtureState.FixturesUnavailable)
        assert(firstState.isLoading)
    }

    @Test
    fun `should emit FixtureAvailable state when API call completes successfully`() = runTest {
        val repository = object : FakeLiveScoreRepository() {
            override suspend fun getFixtures(): OperationOutcome<List<Fixture>> {
                delay(100)
                return OperationOutcome.Success(listOf())
            }
        }
        val viewModel = LiveScoreViewModel(
            repository,
            UnconfinedTestDispatcher()
        )

        viewModel.getFixtures()
        val firstState = viewModel.fixtureUiState.drop(2).first()

        assert(firstState is FixtureState.FixturesAvailable)
        assert(!firstState.isLoading)
        assert(firstState.error == null)
        assert((firstState as FixtureState.FixturesAvailable).fixtures.isEmpty())
    }

    @Test
    fun `should emit FixtureUnavailable state with error when API call fails`() = runTest {
        val repository = object : FakeLiveScoreRepository() {
            override suspend fun getFixtures(): OperationOutcome<List<Fixture>> {
                delay(100)
                return OperationOutcome.Failure(
                    Error(-1, "API Broken")
                )
            }
        }
        val viewModel = LiveScoreViewModel(
            repository,
            UnconfinedTestDispatcher()
        )

        viewModel.getFixtures()
        val firstState = viewModel.fixtureUiState.drop(2).first()

        assert(firstState is FixtureState.FixturesUnavailable)
        assert(!firstState.isLoading)
        assertThat(firstState.error!!.message, `is`("API Broken"))
    }
}