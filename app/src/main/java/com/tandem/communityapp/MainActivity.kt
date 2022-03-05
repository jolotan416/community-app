package com.tandem.communityapp

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import com.tandem.communityapp.community.CommunityMembersAdapter
import com.tandem.communityapp.community.CommunityViewModel
import com.tandem.communityapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val communityViewModel: CommunityViewModel by viewModels()
    private val communityMembersAdapter = CommunityMembersAdapter()

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        configureRecyclerView()
        observeViewModel()
    }

    private fun configureRecyclerView() {
        val colorDrawable =
            ColorDrawable(ContextCompat.getColor(this, R.color.functionGreyIndicator))
        val dividerDecoration =
            DividerItemDecoration(this, DividerItemDecoration.VERTICAL).apply {
                setDrawable(colorDrawable)
            }
        binding.communityMembersRecyclerView.apply {
            adapter = communityMembersAdapter
            addItemDecoration(dividerDecoration)
            setHasFixedSize(true)
        }
    }

    private fun observeViewModel() {
        communityViewModel.displayedCommunityMembers.observe(this) { communityMembers ->
            communityMembersAdapter.setItems(communityMembers)
        }
    }
}