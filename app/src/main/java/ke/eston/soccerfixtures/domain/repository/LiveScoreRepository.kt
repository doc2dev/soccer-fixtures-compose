package ke.eston.soccerfixtures.domain.repository

import com.slack.eithernet.ApiResult
import ke.eston.soccerfixtures.domain.model.Fixture

interface LiveScoreRepository {
    suspend fun getFixtures(): OperationOutcome<List<Fixture>>
}