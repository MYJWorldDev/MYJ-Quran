package com.yousufjamil.myjquran

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
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
import com.yousufjamil.myjquran.featurescreens.AboutScreen
import com.yousufjamil.myjquran.featurescreens.BookmarksScreen
import com.yousufjamil.myjquran.featurescreens.QuranScreen
import com.yousufjamil.myjquran.featurescreens.SettingsScreen
import com.yousufjamil.myjquran.featurescreens.SurahSelectScreen
import com.yousufjamil.myjquran.ui.theme.MYJQuranTheme

class MainActivity : ComponentActivity() {
    lateinit var navController: NavHostController
    lateinit var database: MYJQuranDB
    var langName: String = "english"
    var langCode: String = "english"



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            navController = rememberNavController()
            database = DatabaseProvider.getDatabase(this)
            val context = this

            LaunchedEffect (Unit) {
                if (database.languagesDao.getAllLanguages().size != 1) {
                    for (language in database.languagesDao.getAllLanguages()) {
                        database.languagesDao.deleteLanguage(language)
                    }
                }
                if (database.languagesDao.getAllLanguages().isEmpty()) {
                    database.languagesDao.insertLanguage(
                        getDefaultLang(this@MainActivity)
                    )
                }

                langName = database.languagesDao.getAllLanguages()[0].languageName
                langCode = database.languagesDao.getAllLanguages()[0].languageCode

                DataSource.chapters = getJsonDecodedChapters(context = context, lang = langName)
                DataSource.quran = getJsonDecodedQuran(context = context, lang = langName)
            }

            MYJQuranTheme {
                Column (
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFF607D94))
                ) {
                    Spacer(modifier = Modifier.padding(top = 40.dp))
                    Navigation(navController = navController)
                }
            }
        }
    }

    @Composable
    fun Navigation(navController: NavHostController) {
        DataSource.navController = navController
        DataSource.database = database

        val context = this

        DataSource.chapters = getJsonDecodedChapters(context = context, lang = langName)
        DataSource.quran = getJsonDecodedQuran(context = context, lang = langName)

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

            composable("about") {
                AboutScreen()
            }
        }
    }
}