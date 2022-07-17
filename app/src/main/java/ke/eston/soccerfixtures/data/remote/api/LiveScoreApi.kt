package ke.eston.soccerfixtures.data.remote.api

import com.google.gson.JsonObject
import com.slack.eithernet.ApiResult
import ke.eston.soccerfixtures.data.remote.payload.FixtureResponseJson
import retrofit2.http.GET
import retrofit2.http.Query

interface LiveScoreApiService {
    @GET("fixtures/matches.json")
    suspend fun getFixtures(): ApiResult<FixtureResponseJson, Unit>

    @GET("teams/head2head.json")
    suspend fun getH2HData(
        @Query("team1_id") homeTeamId: String,
        @Query("team2_id") awayTeamId: String
    ): ApiResult<JsonObject, Unit>
}