package com.example.streamchatapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.example.streamchatapp.R
import com.example.streamchatapp.databinding.FragmentChannelListBinding
import com.example.streamchatapp.domain.CreateChannelEvent
import dagger.hilt.android.AndroidEntryPoint
import io.getstream.chat.android.client.models.Filters
import io.getstream.chat.android.ui.channel.list.header.viewmodel.ChannelListHeaderViewModel
import io.getstream.chat.android.ui.channel.list.header.viewmodel.bindView
import io.getstream.chat.android.ui.channel.list.viewmodel.ChannelListViewModel
import io.getstream.chat.android.ui.channel.list.viewmodel.bindView
import io.getstream.chat.android.ui.channel.list.viewmodel.factory.ChannelListViewModelFactory
import kotlinx.coroutines.flow.collect

/**
 * Created by Andronicus Kim on 6/23/22
 */
@AndroidEntryPoint
class ChannelListFragment : BaseFragment<FragmentChannelListBinding>() {

    private val viewModel: ChannelViewModel by activityViewModels()

    override val fragmentBindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentChannelListBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val user = viewModel.getCurrentUser()
        if (user == null) {
            findNavController().popBackStack()
            return
        }

        val factory = ChannelListViewModelFactory(
            // Filter messaging channels only & limit to 30 channels
            filter = Filters.and(Filters.eq("type", "messaging")),
            limit = 30
        )
        val channelListViewModel: ChannelListViewModel by viewModels { factory }
        val channelListHeaderViewModel: ChannelListHeaderViewModel by viewModels()

        // Bind viewmodels to UI
        channelListHeaderViewModel.bindView(binding.channelListHeaderView, viewLifecycleOwner)
        channelListViewModel.bindView(binding.channelListView, viewLifecycleOwner)

        binding.channelListHeaderView.setOnUserAvatarClickListener{
            // sign out user
            viewModel.signOut()
            findNavController().popBackStack()
        }

        binding.channelListView.setChannelItemClickListener{ channel ->
            findNavController().navigate(
                R.id.action_channelFragment_to_chatListFragment,
                Bundle().apply { putString("channelId", channel.cid) }
            )
        }

        binding.channelListHeaderView.setOnActionButtonClickListener{
            findNavController().navigate(
                R.id.action_channelFragment_to_createChannelDialog
            )
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.createChannelEvent.collect { event ->
                when(event) {
                    is CreateChannelEvent.ApiError-> {
                        Toast.makeText(requireContext(),event.error, Toast.LENGTH_LONG).show()
                    }
                    CreateChannelEvent.Success -> {
                        Toast.makeText(requireContext(),"Channel created successfully", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }
}