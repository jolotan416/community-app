package com.tandem.communityapp.community.adapters

import com.tandem.communityapp.data.community.CommunityMember

sealed class CommunityMembersAdapterViewType(val viewType: Int) {
    class ItemViewType(val communityMember: CommunityMember) : CommunityMembersAdapterViewType(0)
    object LoadingViewType : CommunityMembersAdapterViewType(1)
    object ErrorViewType : CommunityMembersAdapterViewType(2)
}
