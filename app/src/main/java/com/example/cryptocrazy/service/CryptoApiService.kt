package com.example.cryptocrazy.service

import com.example.cryptocrazy.model.Crypto
import com.example.cryptocrazy.model.CryptoItem
import com.example.cryptocrazy.model.CryptoModel
import retrofit2.http.GET

interface CryptoApiService {
n
    @GET("atilsamancioglu/IA32-CryptoComposeData/main/cryptolist.json")
    suspend fun getCryptoList():CryptoModel

    

    @GET("atilsamancioglu/IA32-CryptoComposeData/main/crypto.json")
    suspend fun getCrypto():Crypto

}