package com.example.biometricauthentication

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.biometricauthentication.BiometricPromptManager.BiometricResult
import com.example.biometricauthentication.ui.theme.BiometricAuthenticationTheme

class MainActivity : AppCompatActivity() {

    private val promptManager by lazy {
        BiometricPromptManager(this)
    }

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BiometricAuthenticationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    val biometricResult by promptManager.promptResults.collectAsState(initial = null)
                   Column (modifier = Modifier.fillMaxSize(),
                       verticalArrangement = Arrangement.Center,
                       horizontalAlignment = Alignment.CenterHorizontally){
                       
                       Button(onClick = {
                           promptManager.showBiometricPrompt("Login","login to continue")
                       }) {
                           Text(text = "Authenticate")
                       }
                   }
                    biometricResult?.let { result ->
                        Text(
                            text = when(result) {
                                is BiometricResult.AuthenticationError -> {
                                    result.error
                                }
                                BiometricResult.AuthenticationFailed -> {
                                    "Authentication failed"
                                }
                                BiometricResult.AuthenticationNotSet -> {
                                    "Authentication not set"
                                }
                                BiometricResult.AuthenticationSuccess -> {
                                    "Authentication success"
                                }
                                BiometricResult.FeatureUnavailable -> {
                                    "Feature unavailable"
                                }
                                BiometricResult.HardwareUnavailable -> {
                                    "Hardware unavailable"
                                }
                            }
                        )

                    }
                }
            }
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
    BiometricAuthenticationTheme {
        Greeting("Android")
    }
}