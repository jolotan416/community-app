package com.tandem.communityapp.community

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.tandem.communityapp.data.community.CommunityMember
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommunityViewModel @Inject constructor(
    private val repository: CommunityRepository
) : ViewModel() {
    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.requestCommunityMembers()
        }
    }

    val displayedCommunityMembers: LiveData<List<CommunityMember>>
        get() = repository.communityMembers.asLiveData()
}