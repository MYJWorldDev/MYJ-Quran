package com.yousufjamil.myjquran.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface LanguageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLanguage(language: LanguageItemDB)

    @Query("SELECT * FROM language_table")
    suspend fun getAllLanguages(): List<LanguageItemDB>

    @Delete
    suspend fun deleteLanguage(language: LanguageItemDB)
}