package com.example.streamchatapp.ui

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.getstream.chat.android.client.ChatClient
import javax.inject.Inject

/**
 * Created by Andronicus Kim on 6/23/22
 */
@HiltViewModel
class ChannelViewModel @Inject constructor(
    private val chatClient: ChatClient
) : ViewModel() {

    fun signOut() = chatClient.disconnect()

    fun getCurrentUser() = chatClient.getCurrentUser()
}