package ke.eston.soccerfixtures.data.remote.payload

import com.google.gson.annotations.SerializedName
import ke.eston.soccerfixtures.data.remote.model.FixtureJson

data class FixtureWrapper(
    @SerializedName("fixtures")
    val fixtures: List<FixtureJson>
)

data class FixtureResponseJson(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("data")
    val fixtureWrapper: FixtureWrapper,
)