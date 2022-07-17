package ke.eston.soccerfixtures.data.repository

import com.slack.eithernet.ApiResult
import com.slack.eithernet.ApiResult.Failure
import com.slack.eithernet.ApiResult.Failure.*
import com.slack.eithernet.ApiResult.Success
import ke.eston.soccerfixtures.data.remote.api.LiveScoreApiService
import ke.eston.soccerfixtures.domain.model.Fixture
import ke.eston.soccerfixtures.domain.repository.Error
import ke.eston.soccerfixtures.domain.repository.LiveScoreRepository
import ke.eston.soccerfixtures.domain.repository.OperationOutcome

class LiveScoreRepositoryImpl(
    private val apiService: LiveScoreApiService
) : LiveScoreRepository {
    override suspend fun getFixtures(): OperationOutcome<List<Fixture>> {
        return when (val result = apiService.getFixtures()) {
            is Success -> {
                val fixtures = result.value.fixtureWrapper.fixtures
                OperationOutcome.Success(fixtures)
            }
            is Failure -> {
                val error = when (result) {
                    is NetworkFailure -> Error(500, result.error.message)
                    is HttpFailure -> Error(result.code, "Http failure")
                    is ApiFailure -> Error(500, "Parse error")
                    is UnknownFailure -> Error(500, result.error.message)
                }
                OperationOutcome.Failure(error)
            }
        }
    }

    override suspend fun getH2HData(homeTeamId: String, awayTeamId: String) {
        apiService.getH2HData(homeTeamId, awayTeamId)
        // Logging interceptor will log result
    }
}