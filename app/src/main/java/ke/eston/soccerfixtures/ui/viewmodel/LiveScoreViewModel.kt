package ke.eston.soccerfixtures.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ke.eston.soccerfixtures.domain.model.Fixture
import ke.eston.soccerfixtures.domain.repository.Error
import ke.eston.soccerfixtures.domain.repository.LiveScoreRepository
import ke.eston.soccerfixtures.domain.repository.OperationOutcome
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LiveScoreViewModel(
    private val repository: LiveScoreRepository,
    private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {
    private val fixtureState: MutableStateFlow<FixtureState> = MutableStateFlow(
        FixtureState.FixturesUnavailable(isLoading = false)
    )
    val fixtureUiState = fixtureState.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        FixtureState.FixturesUnavailable(isLoading = false)
    )

    fun getFixtures() = viewModelScope.launch(ioDispatcher) {
        fixtureState.update { FixtureState.FixturesUnavailable(isLoading = true) }
        when (val result = repository.getFixtures()) {
            is OperationOutcome.Success -> fixtureState.update {
                FixtureState.FixturesAvailable(
                    isLoading = false,
                    fixtures = result.outcome
                )
            }
            is OperationOutcome.Failure -> fixtureState.update {
                FixtureState.FixturesUnavailable(
                    isLoading = false,
                    error = result.error
                )
            }
        }
    }

    fun getH2HData(fixture: Fixture) = viewModelScope.launch(ioDispatcher) {
        fixture.apply {
            repository.getH2HData(homeTeamId.toString(), awayTeamId.toString())
        }
    }
}

sealed interface FixtureState {
    val isLoading: Boolean
    val error: Error?

    data class FixturesUnavailable(
        override val isLoading: Boolean,
        override val error: Error? = null,
    ) : FixtureState

    data class FixturesAvailable(
        val fixtures: List<Fixture>,
        override val isLoading: Boolean,
        override val error: Error? = null,
    ) : FixtureState
}