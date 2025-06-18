package com.yousufjamil.myjquran.featurescreens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.yousufjamil.myjquran.accessories.getLangCode
import com.yousufjamil.myjquran.accessories.jsondecode.getJsonDecodedChapters
import com.yousufjamil.myjquran.accessories.jsondecode.getJsonDecodedQuran
import com.yousufjamil.myjquran.data.DataSource
import com.yousufjamil.myjquran.data.database.LanguageItemDB
import kotlinx.coroutines.launch

@Preview
@Composable
fun SettingsScreen() {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    Column (
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = "Translation Language",
            color = Color.White,
            fontFamily = DataSource.alKalamiFont,
            fontSize = 60.sp,
            lineHeight = 60.sp,
            textAlign = TextAlign.Center
        )

        val supportedLanguages = listOf(
            "english", "urdu", "bengali", "chinese", "french",
            "indonesian", "russian", "spanish", "swedish", "turkish"
        )

        supportedLanguages.forEach { item ->
            Text(
                text = item.replaceFirstChar { it.uppercase() },
                color = Color.White,
                fontFamily = DataSource.alKalamiFont,
                fontSize = 30.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .clickable {
                        coroutineScope.launch {
                            DataSource.database.languagesDao.getAllLanguages().forEach {
                                DataSource.database.languagesDao.deleteLanguage(it)
                            }

                            DataSource.database.languagesDao.insertLanguage(
                                LanguageItemDB(
                                    languageName = item,
                                    languageCode = getLangCode(item)
                                )
                            )

                            DataSource.chapters = getJsonDecodedChapters(
                                context = context,
                                lang = item
                            )

                            DataSource.quran = getJsonDecodedQuran(
                                context = context,
                                lang = item
                            )

                            DataSource.navController.popBackStack()
                        }
                    }
            )
        }
    }
}