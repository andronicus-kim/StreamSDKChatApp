package com.example.streamchatapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.livedata.ChatDomain
import javax.inject.Inject

/**
 * Created by Andronicus Kim on 6/23/22
 */
@HiltAndroidApp
class StreamChatApp: Application() {

    @Inject
    lateinit var client: ChatClient

    override fun onCreate() {
        super.onCreate()
        ChatDomain.Builder(client,applicationContext).build()
    }
}