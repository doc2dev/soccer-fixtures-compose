package ke.eston.soccerfixtures.ui.fixtures

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ke.eston.soccerfixtures.R
import ke.eston.soccerfixtures.domain.model.Fixture
import ke.eston.soccerfixtures.domain.repository.Error
import ke.eston.soccerfixtures.ui.viewmodel.FixtureState.FixturesAvailable
import ke.eston.soccerfixtures.ui.viewmodel.FixtureState.FixturesUnavailable
import ke.eston.soccerfixtures.ui.viewmodel.LiveScoreViewModel

@Composable
fun FixtureScreen(viewModel: LiveScoreViewModel) {
    val fixtureState by viewModel.fixtureUiState.collectAsState()
    when (fixtureState) {
        is FixturesUnavailable -> FixtureLoadingOrError(state = fixtureState as FixturesUnavailable)
        else -> FixtureView(state = fixtureState as FixturesAvailable) {
            viewModel.getH2HData(it)
        }
    }
}

@Composable
fun FixtureView(
    state: FixturesAvailable,
    onFixtureClick: (Fixture) -> Unit
) {
    val fixtures = state.fixtures
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Fixtures for Today")
            LazyColumn() {
                items(fixtures) { fixture ->
                    FixtureItem(fixture, onFixtureClick)
                }
            }
        }
    }
}

@Composable
fun FixtureItem(
    fixture: Fixture,
    onFixtureClick: (Fixture) -> Unit
) {
    val teamText = "${fixture.homeTeamName} vs ${fixture.awayTeamName}"
    val imgId = R.drawable.ic_soccer_24
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
            .clickable { onFixtureClick(fixture) },
        elevation = 4.dp
    ) {
        Row(modifier = Modifier.padding(4.dp)) {
            Image(
                painter = painterResource(imgId),
                contentDescription = "Soccer ball",
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = teamText)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = fixture.date)
                Text(text = fixture.timeUtc)
            }
        }
    }
}

@Composable
fun FixtureLoadingOrError(state: FixturesUnavailable) {
    if (state.isLoading) {
        LoadingView()
    } else if (state.error != null) {
        ErrorView(error = state.error)
    }
}

@Composable
fun ErrorView(error: Error) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val imgId = R.drawable.error
            Image(
                painter = painterResource(imgId),
                contentDescription = "Error graphic"
            )
            Spacer(modifier = Modifier.height(32.dp))
            Text(text = "An error happened")
            Text(text = "Code:  ${error.code}")
            Text(text = "Error message:  ${error.message}")
        }
    }
}

@Composable
fun LoadingView() {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Loading Fixtures...")
            Spacer(modifier = Modifier.height(32.dp))
            CircularProgressIndicator()
        }
    }
}