package com.yousufjamil.myjquran.featurescreens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.yousufjamil.myjquran.accessories.SelectionListItem
import com.yousufjamil.myjquran.data.DataSource
import com.yousufjamil.myjquran.data.dataStore
import kotlinx.coroutines.launch

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SurahSelectScreen() {
    val surahs = DataSource.chapters
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    LazyColumn (
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        item {
            Text(
                text = "Surahs",
                color = Color.White,
                fontFamily = DataSource.alKalamiFont,
                fontSize = 60.sp,
                textAlign = TextAlign.Center
            )
        }

        items(surahs.size) {index ->
            SelectionListItem(
                title = "${surahs[index].transliteration} - ${surahs[index].name}",
                multilineTitle = true,
                description = "Type: ${surahs[index].type.replaceFirstChar { it.uppercase() }}",
                onClick = {
                    coroutineScope.launch {
                        context.dataStore.edit { preferences ->
                            preferences[stringPreferencesKey("lastReadSurah")] = (index + 1).toString()
                            preferences[stringPreferencesKey("lastReadVerse")] = "1"
                        }
                        DataSource.navController.navigate("quran")
                    }
                }
            )
        }
    }
}