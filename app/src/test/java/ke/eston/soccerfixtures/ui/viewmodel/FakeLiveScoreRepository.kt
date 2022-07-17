package ke.eston.soccerfixtures.ui.viewmodel

import ke.eston.soccerfixtures.domain.model.Fixture
import ke.eston.soccerfixtures.domain.repository.LiveScoreRepository
import ke.eston.soccerfixtures.domain.repository.OperationOutcome

abstract class FakeLiveScoreRepository: LiveScoreRepository {
    override suspend fun getFixtures(): OperationOutcome<List<Fixture>> {
        TODO("Not yet implemented")
    }

    override suspend fun getH2HData(homeTeamId: String, awayTeamId: String) {
        TODO("Not yet implemented")
    }
}