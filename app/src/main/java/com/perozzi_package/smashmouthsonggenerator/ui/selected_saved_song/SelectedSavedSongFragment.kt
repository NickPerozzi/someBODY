package com.perozzi_package.smashmouthsonggenerator.ui.selected_saved_song

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.perozzi_package.smashmouthsonggenerator.R
import com.perozzi_package.smashmouthsonggenerator.copyToClipboard
import com.perozzi_package.smashmouthsonggenerator.data.SavedSong
import com.perozzi_package.smashmouthsonggenerator.databinding.FragmentSelectedSavedSongBinding
import com.perozzi_package.smashmouthsonggenerator.hideKeyboard
import org.koin.androidx.viewmodel.ext.android.viewModel

class SelectedSavedSongFragment : Fragment() {

    private val sssViewModel: SelectedSavedSongViewModel by viewModel()
    private val args: SelectedSavedSongFragmentArgs by navArgs()

    private lateinit var binding: FragmentSelectedSavedSongBinding
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSelectedSavedSongBinding.inflate(layoutInflater)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

        val titleEditText = binding.selectedSongTitle
        val lyricsEditText = binding.selectedSongLyrics

        lyricsEditText.setText(args.savedItemObject.songLyrics)
        titleEditText.setText(args.savedItemObject.songTitle)

        binding.copyButton.setOnClickListener {
            copyToClipboard(
                requireActivity(),
                args.savedItemObject.songTitle,
                args.savedItemObject.songLyrics
            )
        }

        binding.updateButton.setOnClickListener {
            args.savedItemObject.songTitle = titleEditText.text.toString()
            args.savedItemObject.songLyrics = lyricsEditText.text.toString()
            if (args.savedItemObject.songTitle == "") {
                Toast.makeText(
                    context, resources.getString(R.string.give_your_song_a_name),
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }
            if (args.savedItemObject.songLyrics == "") {
                Toast.makeText(
                    context, resources.getString(R.string.you_need_to_generate_lyrics),
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }
            hideKeyboard(requireActivity(), view)
            updateDataInDatabase(args.savedItemObject)
            navController.popBackStack()
        }

        binding.cancelButton.setOnClickListener {
            cancelChanges(args.savedItemObject) }
    }

    private fun updateDataInDatabase(savedSong: SavedSong) {
        sssViewModel.updateSavedSong(savedSong)
        Toast.makeText(
            requireContext(), resources.getString(R.string.song_updated),
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun cancelChanges(savedSong: SavedSong) {
        val builder = androidx.appcompat.app.AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yeah") { _, _ ->
            Toast.makeText(
                requireContext(),
                "No changes made to \"${savedSong.songTitle}\"",
                Toast.LENGTH_SHORT
            ).show()
            navController.popBackStack()
        }
        builder.setNegativeButton("Well hold on") { _, _ ->
            Toast.makeText(
                requireContext(),
                getString(R.string.heck_yeah),
                Toast.LENGTH_SHORT
            ).show()
        }
        builder.setTitle(getString(R.string.cancel_changes_prompt,savedSong.songTitle))
        builder.setMessage(getString(R.string.you_sure_you_want_to_cancel))
        builder.create().show()
    }

}