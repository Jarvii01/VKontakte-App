package com.example.vkapp.di

import android.content.Context
import com.example.vkapp.data.network.ApiFactory
import com.example.vkapp.data.network.ApiService
import com.example.vkapp.data.repository.NewsFeedRepositoryImpl
import com.example.vkapp.domain.repository.NewsFeedRepository
import com.vk.api.sdk.VKPreferencesKeyValueStorage
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {

    @ApplicationScope
    @Binds
    fun bindRepository(impl: NewsFeedRepositoryImpl): NewsFeedRepository

    companion object {

        @ApplicationScope
        @Provides
        fun provideVkStorage(context: Context): VKPreferencesKeyValueStorage {
            return VKPreferencesKeyValueStorage(context)
        }

        @ApplicationScope
        @Provides
        fun provideApiService(): ApiService{
            return ApiFactory.apiService
        }


    }


}