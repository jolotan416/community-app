package com.tandem.communityapp.data.community

import androidx.room.*

@Dao
interface CommunityDao {
    @Query("SELECT * FROM communityMember")
    fun getCommunityMembers(): List<CommunityMember>

    @Query("SELECT * FROM communityMember WHERE id in (:memberIds)")
    fun getCommunityMembers(memberIds: List<String>): List<CommunityMember>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCommunityMembers(communityMembers: List<CommunityMember>)

    @Update
    fun updateCommunityMember(communityMember: CommunityMember)
}