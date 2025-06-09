package com.yousufjamil.myjquran.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "language_table")
data class LanguageItemDB(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val languageName: String,
    val languageCode: String
)
