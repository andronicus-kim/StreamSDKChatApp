package com.example.streamchatapp.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.getstream.chat.android.client.ChatClient
import javax.inject.Singleton

/**
 * Created by Andronicus Kim on 6/23/22
 */

@Module
@InstallIn(SingletonComponent::class)
object AppModule{

    @Singleton
    @Provides
    fun providesChatClient(@ApplicationContext context: Context): ChatClient {
        return ChatClient.Builder(apiKey = "", appContext = context).build()
    }
}