package com.example.cryptocrazy.view


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.cryptocrazy.model.CryptoModelItem
import com.example.cryptocrazy.view_model.CryptoListViewModel

@Composable
//view modeli inject etmek için viewModel:CryptoListViewModel= hiltViewModel() denmesi lazım inject için hilt
fun CryptoListScreen(
    navController: NavController,
    viewModel: CryptoListViewModel = hiltViewModel()
) {

    Surface(
        color = MaterialTheme.colors.secondary,
        modifier = Modifier.fillMaxSize(),

        ) {
        Column() {
            Text(
                text = "crypto Crazy",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                textAlign = TextAlign.Center,
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colors.primary

            )
            Spacer(modifier = Modifier.height(10.dp))

            searchBar(
                hint = "Lütfen bir sey arayıns", modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                 viewModel.searchCryptoList(it);

            }

            Spacer(modifier = Modifier.height(10.dp))
            
            CryptoList(navController = navController)



        }

    }


}

@Composable
fun CryptoList(navController: NavController, viewModel: CryptoListViewModel = hiltViewModel()) {
    val cryptolist by remember {
        viewModel.cryptoList
    }
    val errorMessage by remember {
        viewModel.errorMessage
    }
    val isloading by remember {
        viewModel.isLoading
    }
    cryptoListView(cryptolist, navController = navController)

    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()){
        if (isloading){
            CircularProgressIndicator();
        }
        else if (errorMessage.isNotEmpty()){
            //retyview
            RetryView(error = errorMessage) {
                viewModel.loadCryptos();
            }
        }
    }

}



@Composable
fun searchBar(
    modifier: Modifier = Modifier,
    hint: String = "",
    onSearch: (String) -> Unit = {},
    ) {
    var text by remember {//by = yer,ne kullnılırsa text.value demekten kacnırız
        mutableStateOf("")
    }

    var isHintDisplyed by remember {
        mutableStateOf(hint != "")
    }
    Box(modifier = modifier) {
        BasicTextField(
            value = text,
            onValueChange = {
                text = it;//burda olan her turli canlı degeisimi texte atadım ve onuda onsearch içinde gonderdim
                onSearch(it);
            },
            maxLines = 1,
            singleLine = true,
            textStyle = TextStyle(color = Color.Black),
            modifier = Modifier
                .fillMaxWidth()
                .shadow(5.dp, CircleShape)
                .background(color = Color.White)
                .padding(horizontal = 20.dp, vertical = 12.dp)
                .onFocusChanged {
                    //kulanıcı buraya tıkladıktan sonra ne olsun
                    isHintDisplyed = it.isFocused != true && text.isEmpty()
                },
        )
        if (isHintDisplyed) {
            Text(
                text = hint,
                color = Color.LightGray,
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp)
            )
        }


    }

}

@Composable
fun cryptoRow(navController: NavController, cryptoModelItem: CryptoModelItem) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colors.secondary)
            .clickable {
                navController.navigate("crypto_details_screen/${cryptoModelItem.currency}/${cryptoModelItem.price}")
            },
    ) {
        Text(
            text = cryptoModelItem.currency, style = MaterialTheme.typography.h4,
            modifier = Modifier.padding(2.dp),
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colors.primary

        )
        Text(
            text = cryptoModelItem.price,
            style = MaterialTheme.typography.h5,
            modifier = Modifier.padding(2.dp),
            color = MaterialTheme.colors.primaryVariant
        )
    }
}

@Composable
fun cryptoListView(cryptos: List<CryptoModelItem>, navController: NavController) {
    LazyColumn(contentPadding = PaddingValues(5.dp)) {
        items(cryptos) { crypto ->
            cryptoRow(navController = navController, crypto);

        }
    }
}

@Composable
fun RetryView(error:String,onRety:()->Unit) {
    Column(){
        Text(text = error, color = Color.Red, fontSize = 20.sp, modifier = Modifier.align(Alignment.CenterHorizontally))
        Spacer(modifier = Modifier.height(10.dp))
        Button(onClick = onRety, modifier = Modifier.align(Alignment.CenterHorizontally)){
            Text(text = "Retry")
        }

    }
}