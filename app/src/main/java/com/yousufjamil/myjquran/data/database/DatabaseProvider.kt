package com.yousufjamil.myjquran.data.database

import android.content.Context
import androidx.room.Room

object DatabaseProvider {
    @Volatile
    private var INSTANCE: MYJQuranDB? = null

    fun getDatabase(context: Context): MYJQuranDB {
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                MYJQuranDB::class.java,
                "myj_quran_db"
            ).build()
            INSTANCE = instance
            instance
        }
    }
}