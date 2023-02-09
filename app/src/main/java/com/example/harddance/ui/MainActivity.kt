package com.example.harddance.ui

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.harddance.R
import com.example.harddance.data.entities.TrackList
import com.example.harddance.data.repository.HearthisRepository
import com.example.harddance.databinding.ActivityMainBinding
import com.example.harddance.di.AudioManagerProvider
import com.example.harddance.player.exo.ExoPlayerInstance
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var requestPermission: ActivityResultLauncher<String>
    private lateinit var binding: ActivityMainBinding

    private val mainViewModel: MainViewModel by viewModels()
    @Inject
    lateinit var musicManager: AudioManagerProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        checkPermissions()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mainViewModel.getTrackList()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermission.launch(android.Manifest.permission.POST_NOTIFICATIONS)
        }
        setupObservers()

    }

    private fun checkPermissions() {
        requestPermission = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            if (it) {
                Log.d("Check Permssion", "Granted!")
            } else {
                Toast.makeText(this, "Permission not granted", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupObservers() {
        mainViewModel.trackList.observe(this) {
            setupExoPlayer(it)
        }
    }

    private fun setupUI(cover: String = "", name: String = "") {
        Glide.with(this).load(getString(R.string.cover_uri)).into(binding.ivMainImage)
        Glide.with(this).load(cover).into(binding.ivCover)
        binding.tvSongTitle.text = name
        binding.ivMainImage.setOnClickListener {
            musicManager.getAudioService()?.createNotification()
            mainViewModel.play()
        }

        binding.ivCover.setOnClickListener {
            musicManager.getAudioService()?.disposeNotification()
        }
    }

    private fun setupExoPlayer(list: TrackList) {
        Log.d("Song", list.tracks[0].toString())
        val (name, cover) = list.tracks[0]
        setupUI(name, cover)
    }

}
