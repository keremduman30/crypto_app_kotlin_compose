package com.example.cryptocrazy.view_model

import androidx.lifecycle.ViewModel
import com.example.cryptocrazy.Util.Resource
import com.example.cryptocrazy.model.Crypto
import com.example.cryptocrazy.model.CryptoItem
import com.example.cryptocrazy.model.CryptoModelItem
import com.example.cryptocrazy.repository.CryptoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CryptoDetailsViewModel @Inject constructor(private val repository: CryptoRepository):ViewModel(){

    //burda  bir tane crypto donecek zaten

  suspend  fun getCrypto():Resource<Crypto>{
        return  repository.getCrypto();

    }



}