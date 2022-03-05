package com.tandem.communityapp.data.community

import com.tandem.communityapp.data.database.CommunityAppDatabase
import javax.inject.Inject

class CommunityLocalDataSource @Inject constructor(
    private val communityAppDatabase: CommunityAppDatabase
) {
    fun getCommunityMembers(memberIds: List<String>): List<CommunityMember> =
        communityAppDatabase.getCommunityDao().getCommunityMembers(memberIds)

    fun insertCommunityMembers(communityMembers: List<CommunityMember>) {
        communityAppDatabase.getCommunityDao()
            .insertCommunityMembers(communityMembers)
    }
}