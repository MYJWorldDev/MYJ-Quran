package com.yousufjamil.myjquran.featurescreens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.yousufjamil.myjquran.accessories.VerseDisplay
import com.yousufjamil.myjquran.accessories.jsondecode.Verse
import com.yousufjamil.myjquran.data.DataSource
import com.yousufjamil.myjquran.data.DataSource.context
import com.yousufjamil.myjquran.data.dataStore
import com.yousufjamil.myjquran.data.database.LanguageItemDB
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

//@Preview(showBackground = true, showSystemUi = true)
@Composable
fun QuranScreen() {
    val dataStore = context.dataStore
    val coroutineScope = rememberCoroutineScope()
    val state = rememberLazyListState()

    val lang = remember { mutableStateOf<List<LanguageItemDB>>(emptyList()) }
    val verses = remember { mutableStateOf<List<Verse>>(emptyList()) }
    val lastReadSurah = remember { mutableStateOf("1") }
    val lastReadVerse = remember { mutableStateOf("1") }

    val favFont = remember { mutableStateOf("uthmani") }
    val favFontFile = remember { mutableStateOf(DataSource.uthmaniFont) }

    LaunchedEffect(Unit) {
        lang.value = DataSource.database.languagesDao.getAllLanguages()

        lastReadSurah.value = dataStore.data
            .map { it[stringPreferencesKey("lastReadSurah")] ?: "1" }
            .first()

        lastReadVerse.value = dataStore.data
            .map { it[stringPreferencesKey("lastReadVerse")] ?: "1" }
            .first()

        favFont.value = dataStore.data
            .map { it[stringPreferencesKey("favFont")] ?: "indopak" }
            .first()

        when (favFont.value) {
            "indopak" -> favFontFile.value = DataSource.indoPakFont
            else -> favFontFile.value = DataSource.uthmaniFont
        }

        verses.value = DataSource.quran[lastReadSurah.value.toInt() - 1].verses

        coroutineScope.launch {
            state.scrollToItem(lastReadVerse.value.toInt() - 1)
        }
    }

    LaunchedEffect(state) {
        snapshotFlow { state.firstVisibleItemIndex }
            .distinctUntilChanged()
            .collectLatest { index ->
                context.dataStore.edit { preferences ->
                    preferences[stringPreferencesKey("lastReadVerse")] = (index + 1).toString()
                }
            }
    }

    @Composable
    fun NextButton() {
        val currentSurah = lastReadSurah.value.toInt()
        val nextSurah = currentSurah + 1

        if (nextSurah <= 114) {
            Button(
                onClick = {
                    coroutineScope.launch {
                        context.dataStore.edit { preferences ->
                            preferences[stringPreferencesKey("lastReadSurah")] =
                                nextSurah.toString()
                            preferences[stringPreferencesKey("lastReadVerse")] = "1"
                        }
                        lastReadSurah.value = nextSurah.toString()
                        lastReadVerse.value = "1"
                        verses.value = DataSource.quran[nextSurah - 1].verses
                        state.scrollToItem(0)
                    }
                }
            ) {
                Image(
                    imageVector = Icons.AutoMirrored.Default.KeyboardArrowRight,
                    contentDescription = "Next Surah",
                    colorFilter = ColorFilter.tint(Color.White)
                )
            }
        }
    }

    @Composable
    fun PrevButton() {
        val currentSurah = lastReadSurah.value.toInt()
        val prevSurah = currentSurah - 1

        if (prevSurah >= 1) {
            Button(
                onClick = {
                    coroutineScope.launch {
                        context.dataStore.edit { preferences ->
                            preferences[stringPreferencesKey("lastReadSurah")] =
                                prevSurah.toString()
                            preferences[stringPreferencesKey("lastReadVerse")] = "1"
                        }
                        lastReadSurah.value = prevSurah.toString()
                        lastReadVerse.value = "1"
                        verses.value = DataSource.quran[prevSurah - 1].verses
                        state.scrollToItem(0)
                    }
                }
            ) {
                Image(
                    imageVector = Icons.AutoMirrored.Default.KeyboardArrowLeft,
                    contentDescription = "Previous Surah",
                    colorFilter = ColorFilter.tint(Color.White)
                )
            }
        } else {
            Spacer(modifier = Modifier.padding(8.dp))
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF607D94))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Color(0xFF151C21))
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = DataSource.chapters[lastReadSurah.value.toInt() - 1].translation,
                    textAlign = TextAlign.Start,
                    fontSize = 30.sp,
                    color = Color.White,
                    modifier = Modifier
                        .weight(1f),
                    overflow = TextOverflow.Ellipsis,
                    lineHeight = 40.sp
                )
                Text(
                    text = DataSource.chapters[lastReadSurah.value.toInt() - 1].name,
                    textAlign = TextAlign.End,
                    fontFamily = DataSource.alKalamiFont,
                    fontSize = 30.sp,
                    color = Color.White,
                    modifier = Modifier
                        .weight(1f)
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                PrevButton()
                NextButton()
            }
        }

        LazyColumn(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize(),
            state = state
        ) {
            if (lastReadSurah.value.toInt() != 1) {
                item {
                    VerseDisplay(
                        surahNumber = lastReadSurah.value.toInt(),
                        verseNumber = 0,
                        verse = DataSource.quran[0].verses[0].text,
                        verseTranslation = DataSource.quran[0].verses[0].translation,
                        font = favFontFile.value
                    )
                }
            }

            items(verses.value.size) { index ->
                VerseDisplay(
                    surahNumber = lastReadSurah.value.toInt(),
                    verseNumber = index + 1,
                    verse = verses.value[index].text,
                    verseTranslation = verses.value[index].translation,
                    font = favFontFile.value
                )
            }
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(0.7f)
                        .padding(vertical = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    PrevButton()
                    NextButton()
                }
            }
        }
    }
}
