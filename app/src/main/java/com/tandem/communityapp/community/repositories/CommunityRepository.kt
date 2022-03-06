package com.tandem.communityapp.community.repositories

import com.tandem.communityapp.data.community.CommunityLocalDataSource
import com.tandem.communityapp.data.community.CommunityRemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class CommunityRepository @Inject constructor(
    private val localDataSource: CommunityLocalDataSource,
    private val remoteDataSource: CommunityRemoteDataSource
) {
    companion object {
        private const val MEMBERS_PER_PAGE = 20
    }

    private val mutableCommunityMembers: MutableStateFlow<PagedCommunityMembers> =
        MutableStateFlow(PagedCommunityMembers(listOf(), false))
    val communityMembers: Flow<PagedCommunityMembers> = mutableCommunityMembers

    private var currentPage: Int = 0

    suspend fun requestCommunityMembers() {
        val remoteCommunityMembers = remoteDataSource.getCommunityMembers(currentPage + 1)
        val localCommunityMembers = localDataSource.getCommunityMembers(
            remoteCommunityMembers.map { it.id })
        remoteCommunityMembers.forEach { communityMember ->
            val isLikedLocally =
                localCommunityMembers.find { it.isLiked }?.isLiked ?: return@forEach

            communityMember.isLiked = isLikedLocally
        }
        localDataSource.insertCommunityMembers(remoteCommunityMembers)

        val currentCommunityMembers = mutableCommunityMembers.value.communityMembers
        val isLastPage = remoteCommunityMembers.size < MEMBERS_PER_PAGE
        mutableCommunityMembers.value = PagedCommunityMembers(
            currentCommunityMembers + remoteCommunityMembers, isLastPage
        )
        ++currentPage
    }
}