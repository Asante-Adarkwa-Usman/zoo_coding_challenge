package com.ghost.comcast_coding_assignment.di

import com.ghost.comcast_coding_assignment.data.remote.ApiService
import com.ghost.comcast_coding_assignment.data.repository.AnimalRepositoryImpl
import com.ghost.comcast_coding_assignment.domain.AnimalUseCaseImpl
import com.ghost.comcast_coding_assignment.domain.repository.AnimalRepository
import com.ghost.comcast_coding_assignment.domain.repository.AnimalUseCase
import com.ghost.comcast_coding_assignment.utils.Constants.API_KEY
import com.ghost.comcast_coding_assignment.utils.Constants.BASE_URL
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.Strictness
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    // CoroutineDispatcher
    @Provides
    @Singleton
    fun providesCoroutineDispatcher(): CoroutineDispatcher = Dispatchers.IO

    //Gson
    @Provides
    @Singleton
    fun providesGson(): Gson {
      return  GsonBuilder()
          .setStrictness(Strictness.LENIENT)
            .create()
    }

    //Gson converter
    @Provides
    @Singleton
    fun providesGsonConverterFactory(
        gson: Gson
    ): GsonConverterFactory = GsonConverterFactory.create(gson)

    //Interceptor
    @Provides
    @Singleton
    fun provideOkHttpIntercepter() : HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply{
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    //OkHttp Client
    @Provides
    @Singleton
    fun providesOkHttpClient(
        interceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient
            .Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .addInterceptor{ chain ->
            val request = chain.request().newBuilder()
                .addHeader("X-Api-Key", API_KEY)
                .build()
            chain.proceed(request)
        }
            .addInterceptor(interceptor)
            .build()
    }

    //Retrofit
    @Provides
    @Singleton
    fun providesRetrofit(
        gsonConverter: GsonConverterFactory,
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit
            .Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .addConverterFactory(gsonConverter)
            .build()
    }

    //ApiService
    @Provides
    @Singleton
    fun providesApiService(
        retrofit: Retrofit
    ): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    //Animal Repository
    @Provides
    @Singleton
    fun providesAnimalRepository(
        apiService: ApiService
    ): AnimalRepository = AnimalRepositoryImpl(apiService)

    //Animal Use Case
    @Provides
    @Singleton
    fun providesAnimalUseCase(
        animalRepository: AnimalRepository
    ) : AnimalUseCase = AnimalUseCaseImpl(animalRepository)

}