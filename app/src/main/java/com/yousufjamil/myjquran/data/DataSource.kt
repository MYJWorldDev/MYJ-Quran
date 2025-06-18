package com.yousufjamil.myjquran.data

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.navigation.NavHostController
import com.yousufjamil.myjquran.R
import com.yousufjamil.myjquran.accessories.jsondecode.SurahChapters
import com.yousufjamil.myjquran.accessories.jsondecode.SurahQuran
import com.yousufjamil.myjquran.accessories.jsondecode.getJsonDecodedChapters
import com.yousufjamil.myjquran.data.database.MYJQuranDB

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "myjQuranData")

@SuppressLint("StaticFieldLeak")
object DataSource {
    lateinit var navController: NavHostController

    lateinit var database: MYJQuranDB

    lateinit var chapters: List<SurahChapters>

    lateinit var quran: List<SurahQuran>

    val uthmaniFont = FontFamily(
        Font(R.font.uthmani)
    )

    val indoPakFont = FontFamily(
        Font(R.font.indopak)
    )

    val alKalamiFont = FontFamily(
        Font(R.font.alkalami)
    )
}