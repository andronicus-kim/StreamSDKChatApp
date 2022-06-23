package com.example.streamchatapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.streamchatapp.util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.client.call.await
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Andronicus Kim on 6/23/22
 */
@HiltViewModel
class AuthViewModel @Inject constructor(
    private val chatClient: ChatClient
): ViewModel() {

    private fun isValidUsername(username: String) = username.length >= Constants.MIN_USERNAME_LENGTH

    fun signInUser(username: String){
        viewModelScope.launch {
            if (isValidUsername(username)){
                // Make Network Call
                val result = chatClient.connectGuestUser(username,username).await()
                // Handle Network Response

            }
        }
    }
}