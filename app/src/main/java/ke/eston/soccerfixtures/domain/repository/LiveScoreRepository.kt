package ke.eston.soccerfixtures.domain.repository

import ke.eston.soccerfixtures.domain.model.Fixture

interface LiveScoreRepository {
    suspend fun getFixtures(): OperationOutcome<List<Fixture>>

    suspend fun getH2HData(homeTeamId: String, awayTeamId: String)
}