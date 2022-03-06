package com.tandem.communityapp.data.community

import javax.inject.Inject

class CommunityRemoteDataSource @Inject constructor(
    private val communityApiService: CommunityApiService
) {
    suspend fun getCommunityMembers(page: Int): List<CommunityMember> =
        communityApiService.getCommunityMembers(page).response
}