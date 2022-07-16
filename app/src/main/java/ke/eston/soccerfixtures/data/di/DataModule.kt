package ke.eston.soccerfixtures.data.di

import ke.eston.soccerfixtures.data.CredentialProvider
import ke.eston.soccerfixtures.data.LocalPropertiesCredentialsProvider
import ke.eston.soccerfixtures.data.remote.api.buildApiService
import ke.eston.soccerfixtures.data.repository.LiveScoreRepositoryImpl
import ke.eston.soccerfixtures.domain.repository.LiveScoreRepository
import org.koin.dsl.module

val dataModule = module {
    factory<CredentialProvider> { LocalPropertiesCredentialsProvider(get()) }
    single { buildApiService(get()) }
    factory<LiveScoreRepository> { LiveScoreRepositoryImpl(get()) }
}