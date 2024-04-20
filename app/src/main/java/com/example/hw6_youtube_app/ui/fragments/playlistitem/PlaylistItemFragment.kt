package com.example.hw6_youtube_app.ui.fragments.playlistitem

import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.example.hw6_youtube_app.databinding.FragmentPlaylistItemBinding
import com.example.hw6_youtube_app.ui.adapters.PlaylistsVideosAdapter
import com.example.hw6_youtube_app.ui.base.BaseFragment
import com.example.hw6_youtube_app.ui.viewmodels.PlaylistItemViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.android.ext.android.inject

class PlaylistItemFragment : BaseFragment<
        FragmentPlaylistItemBinding, PlaylistItemViewModel
        >(FragmentPlaylistItemBinding::inflate) {

    override val viewModel: PlaylistItemViewModel by viewModel()
    private val args: PlaylistItemFragmentArgs by navArgs()
    private val adapter: PlaylistsVideosAdapter by inject()

    override fun initialize() = with(binding) {
        super.initialize()
        tvPlaylistTitle.text = args.playlistTitle
        tvPlaylistDesc.text = args.playlistDesc
        rvVideos.adapter = adapter
        viewModel.setupPlaylistId(args.playlistId)
    }

    override fun observe() {
        super.observe()
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getVideos()?.collectLatest {
                adapter.submitData(it)
            }
        }
    }

}