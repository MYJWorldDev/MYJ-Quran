package com.yousufjamil.myjquran.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface BookmarksDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBookmark(bookmark: BookmarksItemDB)

    @Query("SELECT * FROM bookmarks_table")
    suspend fun getAllBookmarks(): List<BookmarksItemDB>

    @Delete
    suspend fun deleteBookmark(bookmark: BookmarksItemDB)
}