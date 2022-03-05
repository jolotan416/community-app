package com.tandem.communityapp.data.community

import com.google.gson.annotations.SerializedName

data class CommunityMembersResponse(
    @SerializedName("response")
    val response: List<CommunityMember>
)