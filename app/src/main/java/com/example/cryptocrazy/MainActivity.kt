package com.example.cryptocrazy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.cryptocrazy.ui.theme.CryptoCrazyTheme
import com.example.cryptocrazy.view.CryptoDetailsScreen
import com.example.cryptocrazy.view.CryptoListScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint //hilt kullanmak için ve bu hangi sayfadan itibarenbaslansın demek
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CryptoCrazyTheme {
                val navController = rememberNavController();
                NavHost(navController = navController, startDestination = "crypto_list_screen") {
                    composable(route = "crypto_list_screen") {
                        CryptoListScreen(navController = navController);
                    }
                    composable(
                        route = "crypto_details_screen/{cryptoId}/{cryptoPrice}",
                        arguments = listOf(
                            navArgument("cryptoId") {
                                type = NavType.StringType
                            },
                            navArgument("cryptoPrice") {
                                type = NavType.StringType
                            },

                            )
                    ) {
                        val cryptoId = remember {
                            it.arguments?.getString("cryptoId")
                        }
                        val cryptoPrice = remember {
                            it.arguments?.getString("cryptoPrice")
                        }

                        CryptoDetailsScreen(
                            id = cryptoId ?: "",
                            price = cryptoPrice ?: "",
                            navController = navController
                        );

                    }


                }

            }
        }
    }
}

