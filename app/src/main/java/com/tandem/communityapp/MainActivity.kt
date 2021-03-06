package com.tandem.communityapp

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.tandem.communityapp.community.adapters.CommunityMembersAdapter
import com.tandem.communityapp.community.viewmodels.CommunityMembersViewState
import com.tandem.communityapp.community.viewmodels.CommunityViewModel
import com.tandem.communityapp.data.community.CommunityMember
import com.tandem.communityapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), CommunityMembersAdapter.Callback {
    private val communityViewModel: CommunityViewModel by viewModels()
    private val communityMembersAdapter = CommunityMembersAdapter(this)

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        configureRecyclerView()
        observeViewModel()
    }

    override fun loadPage() {
        communityViewModel.loadCommunityMembers()
    }

    override fun onClickCommunityMemberLike(communityMember: CommunityMember) {
        communityViewModel.onClickCommunityMemberLike(communityMember)
    }

    override fun onClickReloadButton() {
        communityViewModel.onClickReloadButton()
    }

    private fun configureRecyclerView() {
        val dividerDecoration =
            DividerItemDecoration(this, DividerItemDecoration.VERTICAL).apply {
                ContextCompat.getDrawable(this@MainActivity, R.drawable.function_grey_divider)
                    ?.let { setDrawable(it) }
            }
        binding.communityMembersRecyclerView.apply {
            adapter = communityMembersAdapter.apply {
                registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
                    override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                        super.onItemRangeInserted(positionStart, itemCount)

                        if (positionStart != 0) return

                        binding.communityMembersRecyclerView.scrollToPosition(0)
                    }
                })
            }
            addItemDecoration(dividerDecoration)
            setHasFixedSize(true)
        }
    }

    private fun observeViewModel() {
        communityViewModel.communityMembersViewState.observe(this) { communityMembersViewState ->
            when (communityMembersViewState) {
                is CommunityMembersViewState.LOADING -> communityMembersAdapter.setItems(
                    communityMembersViewState.communityMembers, true
                )
                is CommunityMembersViewState.SUCCESS -> communityMembersAdapter.setItems(
                    communityMembersViewState.communityMembers, false
                )
                is CommunityMembersViewState.ERROR -> communityMembersAdapter.addRetryView()
            }
        }
    }
}