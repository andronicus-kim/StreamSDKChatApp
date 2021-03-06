package com.example.streamchatapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.activity.addCallback
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.viewbinding.ViewBinding
import com.example.streamchatapp.databinding.FragmentChatListBinding
import com.getstream.sdk.chat.viewmodel.MessageInputViewModel
import com.getstream.sdk.chat.viewmodel.messages.MessageListViewModel
import io.getstream.chat.android.ui.message.input.viewmodel.bindView
import io.getstream.chat.android.ui.message.list.header.viewmodel.MessageListHeaderViewModel
import io.getstream.chat.android.ui.message.list.header.viewmodel.bindView
import io.getstream.chat.android.ui.message.list.viewmodel.bindView
import io.getstream.chat.android.ui.message.list.viewmodel.factory.MessageListViewModelFactory

/**
 * Created by Andronicus Kim on 6/24/22
 */
class ChatListFragment: BaseFragment<FragmentChatListBinding>() {

    private val args: ChatListFragmentArgs by navArgs()

    override val fragmentBindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentChatListBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Init factory & viewmodels
        val factory = MessageListViewModelFactory(args.channelId)
        val messageListHeaderViewModel: MessageListHeaderViewModel by viewModels { factory }
        val messageListViewModel: MessageListViewModel by viewModels { factory }
        val messageInputViewModel: MessageInputViewModel by viewModels { factory }

        // Bind viewmodels to UI
        messageListHeaderViewModel.bindView(binding.messageListHeaderView,viewLifecycleOwner)
        messageListViewModel.bindView(binding.messageListView,viewLifecycleOwner)
        messageInputViewModel.bindView(binding.messageInputView,viewLifecycleOwner)

        messageListViewModel.mode.observe(viewLifecycleOwner) { mode ->
            when(mode) {
                is MessageListViewModel.Mode.Thread->{
                    messageListHeaderViewModel.setActiveThread(mode.parentMessage)
                    messageInputViewModel.setActiveThread(mode.parentMessage)
                }
                is MessageListViewModel.Mode.Normal -> {
                    messageListHeaderViewModel.resetThread()
                    messageInputViewModel.resetThread()
                }
            }
        }

        binding.messageListView.setMessageEditHandler(messageInputViewModel::postMessageToEdit)
        messageListViewModel.state.observe(viewLifecycleOwner) {
            if(it == MessageListViewModel.State.NavigateUp) {
                findNavController().navigateUp()
            }
        }
        val backPressHandler = {
            messageListViewModel.onEvent(MessageListViewModel.Event.BackButtonPressed)
        }
        binding.messageListHeaderView.setBackButtonClickListener(backPressHandler)
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            backPressHandler()
        }
    }
}