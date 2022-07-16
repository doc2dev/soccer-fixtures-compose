package ke.eston.soccerfixtures.data.remote.api

import com.google.gson.Gson
import com.slack.eithernet.ApiResultCallAdapterFactory
import com.slack.eithernet.ApiResultConverterFactory
import ke.eston.soccerfixtures.BuildConfig
import ke.eston.soccerfixtures.data.CredentialProvider
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit

fun buildApiService(credentialProvider: CredentialProvider): LiveScoreApiService {
    val retrofit = Retrofit.Builder()
        .baseUrl(credentialProvider.baseUrl())
        .addConverterFactory(ApiResultConverterFactory)
        .addCallAdapterFactory(ApiResultCallAdapterFactory)
        .addConverterFactory(GsonConverterFactory.create(Gson()))
        .client(buildOkClient(credentialProvider))
        .build()
    return retrofit.create(LiveScoreApiService::class.java)
}

private fun buildOkClient(credentialProvider: CredentialProvider): OkHttpClient = OkHttpClient
    .Builder()
    .readTimeout(60, TimeUnit.SECONDS)
    .writeTimeout(60, TimeUnit.SECONDS)
    .addInterceptor(buildQueryParamInterceptor(credentialProvider))
    .addInterceptor(buildLoggingInterceptor())
    .build()

private fun buildQueryParamInterceptor(credentialProvider: CredentialProvider): Interceptor = Interceptor { chain ->
    val original = chain.request()
    val originalHttpUrl = original.url()
    val newUrl = originalHttpUrl.newBuilder()
        .addQueryParameter("key", credentialProvider.apiKey())
        .addQueryParameter("secret", credentialProvider.apiSecret())
        .build()
    val requestBuilder = original.newBuilder()
        .url(newUrl)
    return@Interceptor chain.proceed(requestBuilder.build())
}

private fun buildLoggingInterceptor(): Interceptor = HttpLoggingInterceptor { message ->
    Timber.tag("OkHttp").d(message)
}.apply {
    level = if (BuildConfig.DEBUG) {
        HttpLoggingInterceptor.Level.BODY
    } else {
        HttpLoggingInterceptor.Level.NONE
    }
}