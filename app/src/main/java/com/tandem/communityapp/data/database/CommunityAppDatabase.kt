package com.tandem.communityapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.tandem.communityapp.data.community.CommunityDao
import com.tandem.communityapp.data.community.CommunityMember

@Database(entities = [CommunityMember::class], version = 1)
@TypeConverters(StringListConverter::class)
abstract class CommunityAppDatabase : RoomDatabase() {
    abstract fun getCommunityDao(): CommunityDao
}