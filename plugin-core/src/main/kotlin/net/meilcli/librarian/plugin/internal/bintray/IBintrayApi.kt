package net.meilcli.librarian.plugin.internal.bintray

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import net.meilcli.librarian.plugin.entities.BintrayLicense
import net.meilcli.librarian.plugin.entities.BintrayPackage
import net.meilcli.librarian.plugin.entities.BintraySearchResult
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface IBintrayApi {

    companion object {

        val default = create()

        private fun create(): IBintrayApi {
            val contentType = "application/json".toMediaType()
            val retrofit = Retrofit.Builder()
                .baseUrl("https://api.bintray.com/")
                .addConverterFactory(
                    Json {
                        ignoreUnknownKeys = true
                        isLenient = true
                    }.asConverterFactory(contentType)
                )
                .build()
            return retrofit.create(IBintrayApi::class.java)
        }
    }

    @GET("packages/{owner}/{repository}/{packageName}")
    fun getPackage(
        @Path("owner") owner: String,
        @Path("repository") repository: String,
        @Path("packageName") packageName: String
    ): Call<BintrayPackage>

    @GET("search/packages/maven")
    fun searchPackage(
        @Query("g") group: String,
        @Query("a") artifact: String,
        @Query("subject") owner: String,
        @Query("repo") repository: String
    ): Call<List<BintraySearchResult>>

    @GET("licenses/oss_licenses")
    fun getLicenses(): Call<List<BintrayLicense>>
}