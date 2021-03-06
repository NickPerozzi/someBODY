package com.perozzi_package.smashmouthsonggenerator.ui.album_weights

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.perozzi_package.smashmouthsonggenerator.*
import com.perozzi_package.smashmouthsonggenerator.adapters.AlbumGridAdapter
import com.perozzi_package.smashmouthsonggenerator.databinding.FragmentWeightAssignmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class WeightAssignmentFragment : Fragment(), AlbumGridAdapter.OnSeekBarChangeListenerInterface {

    private val waViewModel: WeightAssignmentViewModel by viewModel()

    private lateinit var binding: FragmentWeightAssignmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWeightAssignmentBinding.inflate(layoutInflater)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        waViewModel.lyricGenerationStatus.value = "Before"

        waViewModel.prepareDataForAdapter()
        activity?.let { waViewModel.prepareAlbumRecyclerView(this, binding.albumRecyclerView, it.application) }

        waViewModel.loadingIconVisibility.value = View.GONE

        Glide.with(this).load(R.drawable.somebody_gif_loading).into(binding.loadingIcon)

        binding.weightAssignmentViewModel = waViewModel
        binding.generateLyricsButton.setOnClickListener {
            binding.generateLyricsButton.isEnabled = false
            if (!waViewModel.areThereAnyNonZeroWeights()) {
                Toast.makeText(
                    context,
                    resources.getString(R.string.you_need_some_weight),
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }
            waViewModel.retrieveLyrics()
        }

        waViewModel.lyricGenerationStatus.observe(viewLifecycleOwner, {
            if (it == "After") {
                try {
                    Navigation.findNavController(view).navigate(
                        R.id.action_weightAssignmentFragment_to_lyricDisplayFragment
                    )
                } catch (e: IllegalArgumentException) {
                    Toast.makeText(
                        context, resources.getString(R.string.your_lyrics_are_ready),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })
    }

    override fun onSeekBarChange(
        position: Int,
        weight: Int,
        textView: TextView,
        albumName: String?
    ) {
        albumName?.let { waViewModel.albumWeightsMap[it] = weight }
        textView.text = resources.getString(R.string.album_weight_xxx, weight.toString())
    }
}