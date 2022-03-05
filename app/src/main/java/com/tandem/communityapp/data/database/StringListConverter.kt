package com.tandem.communityapp.data.database

import androidx.room.TypeConverter

object StringListConverter {
    private const val STRING_DELIMITER = ","

    @TypeConverter
    fun convertStringListToString(stringList: List<String>): String =
        stringList.joinToString(separator = STRING_DELIMITER)

    @TypeConverter
    fun convertStringToStringList(string: String): List<String> =
        string.split(STRING_DELIMITER)
}