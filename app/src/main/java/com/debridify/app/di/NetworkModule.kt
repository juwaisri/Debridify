package com.debridify.app.di

import com.debridify.app.data.api.AllDebridApi
import com.debridify.app.data.api.RealDebridApi
import com.debridify.app.data.api.TorBoxApi
import com.debridify.app.data.model.DebridProvider
import com.debridify.app.data.preferences.PreferencesManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class RealDebridRetrofit

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class TorBoxRetrofit

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AllDebridRetrofit

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    
    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }
    
    @Provides
    @Singleton
    fun provideAuthInterceptor(preferencesManager: PreferencesManager): Interceptor {
        return Interceptor { chain ->
            val request = chain.request()
            val url = request.url.toString()
            
            val provider = when {
                url.contains("real-debrid.com") -> DebridProvider.REAL_DEBRID
                url.contains("torbox.app") -> DebridProvider.TORBOX
                url.contains("alldebrid.com") -> DebridProvider.ALLDEBRID
                else -> null
            }
            
            if (provider != null) {
                val apiKey = runBlocking {
                    preferencesManager.getApiKey(provider).first()
                }
                
                if (!apiKey.isNullOrEmpty()) {
                    val newRequest = when (provider) {
                        DebridProvider.REAL_DEBRID -> {
                            request.newBuilder()
                                .addHeader("Authorization", "Bearer $apiKey")
                                .build()
                        }
                        DebridProvider.TORBOX -> {
                            val newUrl = request.url.newBuilder()
                                .addQueryParameter("api_key", apiKey)
                                .build()
                            request.newBuilder()
                                .url(newUrl)
                                .build()
                        }
                        DebridProvider.ALLDEBRID -> {
                            request.newBuilder()
                                .addHeader("Authorization", "Bearer $apiKey")
                                .build()
                        }
                    }
                    return@Interceptor chain.proceed(newRequest)
                }
            }
            
            chain.proceed(request)
        }
    }
    
    @Provides
    @Singleton
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        authInterceptor: Interceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(loggingInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }
    
    @Provides
    @Singleton
    @RealDebridRetrofit
    fun provideRealDebridRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(DebridProvider.REAL_DEBRID.baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    
    @Provides
    @Singleton
    @TorBoxRetrofit
    fun provideTorBoxRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(DebridProvider.TORBOX.baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    
    @Provides
    @Singleton
    @AllDebridRetrofit
    fun provideAllDebridRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(DebridProvider.ALLDEBRID.baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    
    @Provides
    @Singleton
    fun provideRealDebridApi(@RealDebridRetrofit retrofit: Retrofit): RealDebridApi {
        return retrofit.create(RealDebridApi::class.java)
    }
    
    @Provides
    @Singleton
    fun provideTorBoxApi(@TorBoxRetrofit retrofit: Retrofit): TorBoxApi {
        return retrofit.create(TorBoxApi::class.java)
    }
    
    @Provides
    @Singleton
    fun provideAllDebridApi(@AllDebridRetrofit retrofit: Retrofit): AllDebridApi {
        return retrofit.create(AllDebridApi::class.java)
    }
}
