package com.example.cryptocrazy.view_model

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptocrazy.Util.Resource
import com.example.cryptocrazy.model.CryptoModelItem
import com.example.cryptocrazy.repository.CryptoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CryptoListViewModel @Inject constructor(private val repository: CryptoRepository) :ViewModel(){

    //burda live data yerine composezun bize verdigi mutableStateOf kullanacaz

    var cryptoList= mutableStateOf<List<CryptoModelItem>>(listOf())
    var errorMessage= mutableStateOf("")
    var isLoading= mutableStateOf(false);
    private var initialCryptoList= listOf<CryptoModelItem>();
    private var isSearchStarting=true;

    //veri uzerinde search yapabilmek için oncelikle bu verinin indrilmesi lazım
    //bunun için initte loadCryptos u ekledik

    init {
        loadCryptos();//bu viewmodel yuklenir yuklenmez veriler indirilsin


    }


    fun searchCryptoList(query:String){
        val  listToSearch=if (isSearchStarting){
                cryptoList.value
        }else initialCryptoList


        //simdi arama kısmını coroutines ile yapcaz bu listeyi taradıgı için default olanı secez işlemci olayları yani
        viewModelScope.launch(Dispatchers.Default){
            if (query.isEmpty()){
                //simdi arama eger varsa biz indirdigimiz yani cryptolist i gosterecez
                    //ama eger yapmıyorsa biz yedek kayıt  olan initailicryptou atıyacaz
                cryptoList.value=initialCryptoList;
                isSearchStarting=true;
                return@launch;
            }
           // eger query bos degilse ve hakketten birsey aranıyorsa bunu cryptolist uzerinde filtreleme yaparak yapacaz
            val results=listToSearch.filter {
                it.currency.contains(query.trim(), ignoreCase = true)// ignore buyuk ve kucuk harfe karısmaz

            }

            if (isSearchStarting){
                initialCryptoList=cryptoList.value;
                isSearchStarting=false;
            }
            cryptoList.value=results;

        }
    }


    fun loadCryptos(){
        //simdi verileri cekmek için repoda suspend ve biz coroutinesiçinde yapmak lazım bunun viewmodelda
        //kullanmak için 2 yol var biri viewmodelscope  digeri bufonksypnususpend yapıp view tarafında launch ile cagırmak
        //ikisinide gorecez
       viewModelScope.launch {
           isLoading.value=true;
           val result=repository.getCrytoList();
           when(result){
               is Resource.Success->{
                    val cryptoItems=result.data!!.map {
                      CryptoModelItem(it.currency,it.price);
                    }
                   errorMessage.value=""
                   isLoading.value=false;
                   cryptoList.value=cryptoItems;

               }

               is Resource.Error->{
                   errorMessage.value=result.message.toString();
                   isLoading.value=false;

               }
           }
       }
    }


}