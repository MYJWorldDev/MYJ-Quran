package com.yousufjamil.myjquran.data

import android.content.Context
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.navigation.NavHostController
import com.yousufjamil.myjquran.R
import com.yousufjamil.myjquran.accessories.jsondecode.SurahChapters
import com.yousufjamil.myjquran.accessories.jsondecode.SurahQuran
import com.yousufjamil.myjquran.accessories.jsondecode.getJsonDecodedChapters
import com.yousufjamil.myjquran.data.database.MYJQuranDB

object DataSource {
    lateinit var navController: NavHostController

    lateinit var database: MYJQuranDB

    lateinit var context: Context

    val uthmaniFont = FontFamily(
        Font(R.font.uthmani)
    )

    val indoPakFont = FontFamily(
        Font(R.font.indopak)
    )

    val alKalamiFont = FontFamily(
        Font(R.font.alkalami)
    )

    lateinit var chapters: List<SurahChapters>

    lateinit var quran: List<SurahQuran>
}