package ke.eston.soccerfixtures.ui.di

import ke.eston.soccerfixtures.ui.viewmodel.LiveScoreViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    factory { Dispatchers.IO }
    viewModel { LiveScoreViewModel(get(), get()) }
}