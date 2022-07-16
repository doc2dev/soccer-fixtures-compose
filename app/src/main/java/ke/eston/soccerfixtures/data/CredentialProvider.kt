package ke.eston.soccerfixtures.data

import android.content.Context
import ke.eston.soccerfixtures.R

interface CredentialProvider {
    fun apiKey(): String
    fun apiSecret(): String
    fun baseUrl(): String
}

class LocalPropertiesCredentialsProvider(private val context: Context) : CredentialProvider {
    override fun apiKey(): String =
        context.getString(R.string.livescore_api_key)

    override fun apiSecret(): String =
        context.getString(R.string.livescore_api_secret)


    override fun baseUrl(): String =
        context.getString(R.string.livescore_api_url)
}