package com.example.singpasspoc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.net.toUri
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navDeepLink
import com.example.singpasspoc.ui.theme.SingpassPoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SingpassPoTheme {
                // A surface container using the 'background' color from the theme
                val navController = rememberNavController()
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SingpassNavHost(navController)
                }
            }
        }
    }
}

@Composable
fun SingpassNavHost(navController: NavHostController) {
    val uri = "https://www.example.com"

    NavHost(navController = navController, startDestination = "areYouResident") {
        composable("areYouResident") {
            AreYouResidentScreen(
                onNavigateResident = {
                    navController.navigate(deepLink = "$uri/HELLO_WORLD".toUri())
                }
            )
        }

        composable(
            route = "residentInfo?authCode={authCode}",
            deepLinks = listOf(navDeepLink { uriPattern = "$uri/{authCode}" })
        ) { backStackEntry ->
            ResidentInfoScreen(backStackEntry.arguments?.getString("authCode") ?: "")
        }

        composable("residentAddress") {
            ResidentAddressScreen()
        }
    }
}

@Composable
fun ResidentAddressScreen() {
    Column {
        Text(text = "resident address appears here")
    }
}

@Composable
fun ResidentInfoScreen(authCode: String) {
    Column {
        Text(text = "User authCode = $authCode")
    }
}

@Composable
fun AreYouResidentScreen(onNavigateResident: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize()) {
        Button(onClick = { onNavigateResident() }) {
            Text(text = "Are you resident?")
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SingpassPoTheme {
        Greeting("Android")
    }
}