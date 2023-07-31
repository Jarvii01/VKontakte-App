package com.example.vkapp.presentation

import android.app.Application
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.example.vkapp.di.ApplicationComponent
import com.example.vkapp.di.DaggerApplicationComponent

class NewsFeedApplication : Application() {

    val component by lazy {
        DaggerApplicationComponent.factory().create(this)
    }

}


@Composable
fun getApplicationComponent(): ApplicationComponent {
    return (LocalContext.current.applicationContext as NewsFeedApplication).component
}
