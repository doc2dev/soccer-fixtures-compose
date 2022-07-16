package ke.eston.soccerfixtures.data.remote.api

import com.slack.eithernet.ApiResult
import ke.eston.soccerfixtures.data.remote.payload.FixtureResponseJson
import retrofit2.http.GET

interface LiveScoreApiService {
    @GET("fixtures/matches.json")
    suspend fun getFixtures(): ApiResult<FixtureResponseJson, Unit>
}