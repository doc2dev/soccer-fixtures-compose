package ke.eston.soccerfixtures

import android.content.Context
import ke.eston.soccerfixtures.data.di.dataModule
import ke.eston.soccerfixtures.ui.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.logger.EmptyLogger

fun initKoin(context: Context) {
    val modules = listOf(
        dataModule,
        viewModelModule
    )
    startKoin {
        EmptyLogger()
        androidContext(context)
        modules(modules)
    }
}