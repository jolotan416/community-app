package com.tandem.communityapp.community.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tandem.communityapp.community.repositories.CommunityRepository
import com.tandem.communityapp.data.community.CommunityMember
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CommunityViewModel @Inject constructor(
    private val repository: CommunityRepository
) : ViewModel() {
    init {
        observeRepository()
    }

    private val mutableCommunityMembersViewState: MutableLiveData<CommunityMembersViewState> by lazy {
        MutableLiveData<CommunityMembersViewState>()
    }

    val communityMembersViewState: LiveData<CommunityMembersViewState> =
        mutableCommunityMembersViewState

    fun loadCommunityMembers() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repository.requestCommunityMembers()
            } catch (exception: Exception) {
                withContext(Dispatchers.Main) {
                    mutableCommunityMembersViewState.value = CommunityMembersViewState.ERROR
                }
            }
        }
    }

    fun onClickCommunityMemberLike(communityMember: CommunityMember) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.toggleCommunityMemberLike(communityMember)
        }
    }

    fun onClickReloadButton() {
        CoroutineScope(Dispatchers.Main).launch {
            repository.communityMembers.collect {
                if (it.isLastPage) return@collect

                mutableCommunityMembersViewState.value =
                    CommunityMembersViewState.LOADING(it.communityMembers)
            }
        }
    }

    private fun observeRepository() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.communityMembers.collect {
                withContext(Dispatchers.Main) {
                    mutableCommunityMembersViewState.value =
                        if (it.isLastPage) CommunityMembersViewState.SUCCESS(it.communityMembers)
                        else CommunityMembersViewState.LOADING(it.communityMembers)
                }
            }
        }
    }
}