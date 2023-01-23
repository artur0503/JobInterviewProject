package com.mikhnovskiy.spacex.di.modules

import com.google.gson.GsonBuilder
import com.mikhnovskiy.spacex.BuildConfig
import com.mikhnovskiy.spacex.data.network.Api
import com.mikhnovskiy.spacex.data.network.interceptor.HeaderModifierInterceptor
import com.mikhnovskiy.spacex.data.network.interceptor.QueryModifierInterceptor
import com.mikhnovskiy.spacex.data.repository.NetworkRepository
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideNetworkRepository(api: Api): NetworkRepository {
        return NetworkRepository(api)
    }

    @Provides
    @Singleton
    fun provideApi(client: Retrofit): Api {
        return client.create(Api::class.java)
    }

    @Provides
    @Singleton
    fun provideClient(httpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL + BuildConfig.API_VERSION)
            .client(httpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
    }

    @Provides
    @Singleton
    fun provideHeaderModifierInterceptor(): Interceptor {
        return HeaderModifierInterceptor()
    }

    @Provides
    @Singleton
    fun provideQueryModifierInterceptor(): Interceptor {
        return QueryModifierInterceptor()
    }

    @Provides
    @Singleton
    fun createClient(headerInterceptor: HeaderModifierInterceptor, queryInterceptor: QueryModifierInterceptor): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .addInterceptor(headerInterceptor)
            .addInterceptor(queryInterceptor)
            .followSslRedirects(true)
            .followRedirects(true)
            .connectTimeout(5, TimeUnit.SECONDS)
            .readTimeout(5, TimeUnit.SECONDS)
            .writeTimeout(5, TimeUnit.SECONDS)

        if (BuildConfig.DEBUG) {
            val logger: HttpLoggingInterceptor =
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            builder.addInterceptor(logger)
        }

        return builder.build()
    }
}