package com.example.streamchatapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.streamchatapp.domain.CreateChannelEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.client.call.await
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

/**
 * Created by Andronicus Kim on 6/23/22
 */
@HiltViewModel
class ChannelViewModel @Inject constructor(
    private val chatClient: ChatClient
) : ViewModel() {

    private val _createChannelEvent = MutableSharedFlow<CreateChannelEvent>()
    val createChannelEvent = _createChannelEvent.asSharedFlow()

    fun signOut() = chatClient.disconnect()

    fun getCurrentUser() = chatClient.getCurrentUser()

    fun createChannel(channelName: String) {
        viewModelScope.launch {
            val response = chatClient.channel(
                "messaging",
                UUID.randomUUID().toString()
            ).create(
                mapOf(
                    "name" to channelName
                )
            ).await()
            if (response.isError) {
                _createChannelEvent.emit(CreateChannelEvent.ApiError(response.error().message ?: "An error occurred!"))
                return@launch
            }
            _createChannelEvent.emit(CreateChannelEvent.Success)
        }
    }
}