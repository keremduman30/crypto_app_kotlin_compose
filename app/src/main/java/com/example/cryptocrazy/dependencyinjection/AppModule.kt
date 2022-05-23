package com.example.cryptocrazy.dependencyinjection

import com.example.cryptocrazy.Util.Constants
import com.example.cryptocrazy.repository.CryptoRepository
import com.example.cryptocrazy.service.CryptoApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)//bu app modulumn butun appte ulasılması
object AppModule {
    //build işlemlerin hepsi burada yapılcak ve artık her sınıfta ayrı ayrı build etmeye gerek kalmıyacak

    @Singleton
    @Provides
    fun provideCryptoRepository(apiService: CryptoApiService):CryptoRepository{
        return  CryptoRepository(apiService = apiService);
    }

    @Singleton
    @Provides
    fun provideCryptoApi():CryptoApiService{
        return  Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Constants.BASE_URL)
            .build()
            .create(CryptoApiService::class.java)

    }


}