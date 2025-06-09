package com.yousufjamil.myjquran.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bookmarks_table")
data class BookmarksItemDB(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val bookmarkName: String,
    val surahNo: Int,
    val verseNo: Int,
    val bookmarkTime: Long = System.currentTimeMillis()
)
