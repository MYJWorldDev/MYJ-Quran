package com.yousufjamil.myjquran.data

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.navigation.NavHostController
import com.yousufjamil.myjquran.R

object DataSource {
    lateinit var navController: NavHostController

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