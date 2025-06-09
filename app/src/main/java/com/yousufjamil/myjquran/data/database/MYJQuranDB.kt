package com.yousufjamil.myjquran.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        BookmarksItemDB::class,
        LanguageItemDB::class
    ],
    version = 1
)
abstract class MYJQuranDB: RoomDatabase() {
    abstract val bookmarksDao: BookmarksDao
    abstract val languagesDao: LanguageDao
}