package com.example.vkapp.di

import androidx.lifecycle.ViewModel
import com.example.vkapp.presentation.comment.CommentsViewModel
import com.example.vkapp.presentation.main.MainViewModel
import com.example.vkapp.presentation.news.NewsScreenViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @IntoMap
    @ViewModelKey(MainViewModel::class)
    @Binds
    fun bindMainViewModel(viewModel: MainViewModel): ViewModel

    @IntoMap
    @ViewModelKey(NewsScreenViewModel::class)
    @Binds
    fun bindNewsScreenViewModel(viewModel: NewsScreenViewModel): ViewModel




}