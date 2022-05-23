package com.example.cryptocrazy.repository

import com.example.cryptocrazy.Util.Resource
import com.example.cryptocrazy.model.Crypto
import com.example.cryptocrazy.model.CryptoItem
import com.example.cryptocrazy.model.CryptoModel
import com.example.cryptocrazy.service.CryptoApiService
import dagger.hilt.android.scopes.ActivityScoped
import java.lang.Exception
import javax.inject.Inject

@ActivityScoped
class CryptoRepository @Inject constructor(
    private val apiService:CryptoApiService
) {
    //yapılacak işlemlerin fonksiyonları burada yapılır

    suspend fun getCrytoList():Resource<CryptoModel>{
        val response=try {
            apiService.getCryptoList();

        }
        catch (e:Exception){
            return  Resource.Error("Error : ${e.toString()}")
        }
        return  Resource.Success(response);
    }

    suspend fun getCrypto():Resource<Crypto>{
        val response=try {
            apiService.getCrypto();
        }
        catch (e:Exception){
          return  Resource.Error("Hata : ${e.toString()}")
        }
        return  Resource.Success(response);
    }

}