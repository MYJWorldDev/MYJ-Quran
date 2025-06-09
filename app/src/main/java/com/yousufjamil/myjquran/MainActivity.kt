package com.yousufjamil.myjquran

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.yousufjamil.myjquran.accessories.getDefaultLang
import com.yousufjamil.myjquran.accessories.jsondecode.getJsonDecodedChapters
import com.yousufjamil.myjquran.accessories.jsondecode.getJsonDecodedQuran
import com.yousufjamil.myjquran.data.DataSource
import com.yousufjamil.myjquran.data.database.DatabaseProvider
import com.yousufjamil.myjquran.data.database.MYJQuranDB
import com.yousufjamil.myjquran.featurescreens.BookmarksScreen
import com.yousufjamil.myjquran.featurescreens.QuranScreen
import com.yousufjamil.myjquran.featurescreens.SettingsScreen
import com.yousufjamil.myjquran.featurescreens.SurahSelectScreen
import com.yousufjamil.myjquran.ui.theme.MYJQuranTheme

class MainActivity : ComponentActivity() {
    lateinit var navController: NavHostController
    lateinit var database: MYJQuranDB
    lateinit var context: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            navController = rememberNavController()
            database = DatabaseProvider.getDatabase(this)
            context = this

            LaunchedEffect (Unit) {
                if (database.languagesDao.getAllLanguages().isEmpty()) {
                    database.languagesDao.insertLanguage(
                        getDefaultLang(this@MainActivity)
                    )
                }
            }

            MYJQuranTheme {
                Navigation(navController = navController)
            }
        }
    }

    @Composable
    fun Navigation(navController: NavHostController) {
        DataSource.navController = navController
        DataSource.database = database
        DataSource.context = context

        DataSource.chapters = getJsonDecodedChapters(context, "en")
        DataSource.quran = getJsonDecodedQuran(context, "en")

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
}