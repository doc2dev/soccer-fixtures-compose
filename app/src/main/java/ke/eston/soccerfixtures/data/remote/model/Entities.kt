package ke.eston.soccerfixtures.data.remote.model

import com.google.gson.annotations.SerializedName
import ke.eston.soccerfixtures.domain.model.Competition
import ke.eston.soccerfixtures.domain.model.Fixture
import ke.eston.soccerfixtures.domain.model.League

data class LeagueJson(
    @SerializedName("id")
    override val leagueId: Int,
    @SerializedName("name")
    override val leagueName: String,
): League()

data class CompetitionJson(
    @SerializedName("id")
    override val competitionId: Int,
    @SerializedName("name")
    override val competitionName: String,
): Competition()

data class FixtureJson(
    @SerializedName("id")
    override val fixtureId: Int,
    @SerializedName("home_id")
    override val homeTeamId: Int,
    @SerializedName("home_name")
    override val homeTeamName: String,
    @SerializedName("away_id")
    override val awayTeamId: Int,
    @SerializedName("away_name")
    override val awayTeamName: String,
    @SerializedName("location")
    override val location: String,
    @SerializedName("date")
    override val date: String,
    @SerializedName("time")
    override val timeUtc: String,
    @SerializedName("round")
    override val round: String,
    @SerializedName("league")
    override val league: LeagueJson,
    @SerializedName("competition")
    override val competition: CompetitionJson,
    @SerializedName("h2h")
    override val headToHeadUrl: String,
): Fixture()