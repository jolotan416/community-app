package com.tandem.communityapp.data

import android.content.Context
import androidx.room.Room
import com.tandem.communityapp.BuildConfig
import com.tandem.communityapp.data.community.CommunityApiService
import com.tandem.communityapp.data.database.CommunityAppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {
    private const val BASE_URL = "https://tandem2019.web.app/api/"
    private const val DATABASE_NAME = "community-app-database"

    @Provides
    fun provideCommunityApiService(): CommunityApiService {
        val okHttpClientBuilder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            okHttpClientBuilder.addInterceptor(
                HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY)
            )
        }

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClientBuilder.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CommunityApiService::class.java)
    }

    @Provides
    fun provideCommunityAppDatabase(@ApplicationContext context: Context): CommunityAppDatabase =
        Room.databaseBuilder(context, CommunityAppDatabase::class.java, DATABASE_NAME)
            .build()
}