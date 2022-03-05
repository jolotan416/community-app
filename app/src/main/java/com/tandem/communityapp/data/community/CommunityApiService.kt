package com.tandem.communityapp.data.community

import retrofit2.http.GET
import retrofit2.http.Path

interface CommunityApiService {
    @GET("community_{page}.json")
    suspend fun getCommunityMembers(@Path("page") page: Int): CommunityMembersResponse
}