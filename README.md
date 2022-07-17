# Soccer Fixtures
App that displays soccer fixtures. Because... why not 🙂

## Requirements to run
Add the following values to your `local.properties`:
- LIVESCORE_API_URL = https://livescore-api.com/api-client/
- LIVESCORE_API_KEY = your_api_key
- LIVESCORE_API_SECRET = your_api_secret

## Libraries and technologies
- [Timber](https://github.com/JakeWharton/timber) for logging
- [Retrofit](https://square.github.io/retrofit/) for network calls
- [Eithernet](https://github.com/slackhq/EitherNet) to model network responses as a sealed API result type
- [Koin](https://insert-koin.io/) for dependency injection
- [Jetpack Compose](https://developer.android.com/jetpack/compose) for UI
- [State Flow](https://developer.android.com/kotlin/flow/stateflow-and-sharedflow) to model observable streams
