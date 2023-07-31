package com.example.vkapp.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.vkapp.domain.entity.AuthState
import com.example.vkapp.presentation.getApplicationComponent
import com.example.vkapp.ui.theme.VkAppTheme
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKScope

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val component = getApplicationComponent()
            VkAppTheme {
                val viewModel: MainViewModel = viewModel(factory = component.getViewModelFactory())
                val authState = viewModel.authState.collectAsState(AuthState.Initial)


                val launcher = rememberLauncherForActivityResult(
                    contract = VK.getVKAuthActivityResultContract()
                ) {
                    viewModel.performAuthResult()
                }

                when (authState.value) {
                    is AuthState.Authorized -> {
                        MainScreen(viewModelFactory = component.getViewModelFactory())
                    }

                    else -> {
                        LoginScreen(
                            onLoginClick = {
                                launcher.launch(listOf(VKScope.WALL, VKScope.FRIENDS))
                            }
                        )
                    }


                }

            }
        }
    }
}
