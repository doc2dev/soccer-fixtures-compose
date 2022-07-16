package ke.eston.soccerfixtures.domain.model


abstract class Fixture {
    abstract val fixtureId: Int
    abstract val homeTeamId: Int
    abstract val homeTeamName: String
    abstract val awayTeamId: Int
    abstract val awayTeamName: String
    abstract val location: String
    abstract val date: String
    abstract val timeUtc: String
    abstract val round: String
    abstract val league: League
    abstract val competition: Competition
    abstract val headToHeadUrl: String
}