package com.tandem.communityapp.community.repositories

import com.tandem.communityapp.data.community.CommunityMember

data class PagedCommunityMembers(
    val communityMembers: List<CommunityMember>,
    val isLastPage: Boolean
)
