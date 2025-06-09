package com.yousufjamil.myjquran

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.yousufjamil.myjquran.data.DataSource
import com.yousufjamil.myjquran.featurescreens.BookmarksScreen
import com.yousufjamil.myjquran.featurescreens.QuranScreen
import com.yousufjamil.myjquran.featurescreens.SettingsScreen
import com.yousufjamil.myjquran.featurescreens.SurahSelectScreen
import com.yousufjamil.myjquran.ui.theme.MYJQuranTheme

class MainActivity : ComponentActivity() {
    lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            navController = rememberNavController()

            MYJQuranTheme {
                Navigation(navController = navController)
            }
        }
    }
}

@Composable
fun Navigation(navController: NavHostController) {
    DataSource.navController = navController

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen()
        }

        composable("quran") {
            QuranScreen()
        }

        composable("surahSelect") {
            SurahSelectScreen()
        }

        composable ("bookmarks") {
            BookmarksScreen()
        }

        composable("settings") {
            SettingsScreen()
        }
    }
}