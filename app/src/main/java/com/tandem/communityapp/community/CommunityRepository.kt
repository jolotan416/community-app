package com.tandem.communityapp.community

import com.tandem.communityapp.data.community.CommunityLocalDataSource
import com.tandem.communityapp.data.community.CommunityMember
import com.tandem.communityapp.data.community.CommunityRemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class CommunityRepository @Inject constructor(
    private val localDataSource: CommunityLocalDataSource,
    private val remoteDataSource: CommunityRemoteDataSource
) {
    private val mutableCommunityMembers: MutableStateFlow<List<CommunityMember>> =
        MutableStateFlow(listOf())
    val communityMembers: Flow<List<CommunityMember>> = mutableCommunityMembers

    suspend fun requestCommunityMembers() {
        val remoteCommunityMembers = remoteDataSource.getCommunityMembers()
        val localCommunityMembers = localDataSource.getCommunityMembers(
            remoteCommunityMembers.map { it.id })
        remoteCommunityMembers.forEach {
            val isLikedLocally =
                localCommunityMembers.find { it.isLiked }?.isLiked ?: return@forEach

            it.isLiked = isLikedLocally
        }
        localDataSource.insertCommunityMembers(remoteCommunityMembers)

        mutableCommunityMembers.value = remoteCommunityMembers
    }
}