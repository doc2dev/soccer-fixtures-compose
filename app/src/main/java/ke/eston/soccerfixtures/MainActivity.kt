package ke.eston.soccerfixtures

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import ke.eston.soccerfixtures.ui.fixtures.FixtureScreen
import ke.eston.soccerfixtures.ui.theme.SoccerFixturesTheme
import ke.eston.soccerfixtures.ui.viewmodel.LiveScoreViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {
    private val liveScoreViewModel: LiveScoreViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val id = R.drawable.error
        setContent {
            SoccerFixturesTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    FixtureScreen(viewModel = liveScoreViewModel)
                }
            }
        }
        liveScoreViewModel.getFixtures()
    }
}