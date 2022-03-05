package com.tandem.communityapp

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.tandem.communityapp.community.CommunityViewModel
import com.tandem.communityapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val communityViewModel: CommunityViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        observeViewModel()
    }

    private fun observeViewModel() {
        communityViewModel.displayedCommunityMembers.observe(this) {
            // TODO: Add handling of displayed commmunity members
        }
    }
}