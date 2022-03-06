package com.tandem.communityapp.community.viewmodels

import com.tandem.communityapp.data.community.CommunityMember

sealed class CommunityMembersViewState {
    class LOADING(val communityMembers: List<CommunityMember>) : CommunityMembersViewState()
    class SUCCESS(val communityMembers: List<CommunityMember>) : CommunityMembersViewState()
    object ERROR : CommunityMembersViewState()
}