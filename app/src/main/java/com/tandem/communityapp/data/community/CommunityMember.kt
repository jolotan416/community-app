package com.tandem.communityapp.data.community

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class CommunityMember(
    @SerializedName("id")
    @PrimaryKey(autoGenerate = false)
    val id: String,

    @SerializedName("topic")
    @ColumnInfo(name = "topic")
    val topic: String,

    @SerializedName("firstName")
    @ColumnInfo(name = "firstName")
    val firstName: String,

    @SerializedName("pictureUrl")
    @ColumnInfo(name = "pictureUrl")
    val pictureUrl: String,

    @SerializedName("natives")
    @ColumnInfo(name = "natives")
    val nativeLanguages: List<String>,

    @SerializedName("learns")
    @ColumnInfo(name = "learns")
    val learnedLanguages: List<String>,

    @SerializedName("referenceCnt")
    @ColumnInfo(name = "referenceCnt")
    val referenceCount: Int,

    @ColumnInfo(name = "isLiked")
    var isLiked: Boolean = false
)
