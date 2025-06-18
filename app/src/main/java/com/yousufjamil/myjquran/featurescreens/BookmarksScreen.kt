package com.yousufjamil.myjquran.featurescreens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.yousufjamil.myjquran.data.database.BookmarksItemDB
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun BookmarksScreen() {
    var bookmarks = remember { mutableStateOf<List<BookmarksItemDB>>(emptyList()) }
//    var bookmarks = remember {
//        mutableStateOf<List<BookmarksItemDB>>(
//            listOf(
//                BookmarksItemDB(
//                    id = 1,
//                    bookmarkName = "Test",
//                    surahNo = 1,
//                    verseNo = 1,
//                    bookmarkTime = 1302121200L
//                ),
//                BookmarksItemDB(
//                    id = 1,
//                    bookmarkName = "Test",
//                    surahNo = 1,
//                    verseNo = 1,
//                    bookmarkTime = 1302121200L
//                ),
//                BookmarksItemDB(
//                    id = 1,
//                    bookmarkName = "Test",
//                    surahNo = 1,
//                    verseNo = 1,
//                    bookmarkTime = 1302121200L
//                )
//            )
//        )
//    }

    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        bookmarks.value = DataSource.database.bookmarksDao.getAllBookmarks()
    }

    LazyColumn(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF607D94))
    ) {
        item {
            Text(
                text = "Bookmarks",
                color = Color.White,
                fontFamily = DataSource.alKalamiFont,
                fontSize = 60.sp,
                textAlign = TextAlign.Center
            )
        }
        items(bookmarks.value.size) { index ->
            val formatter = SimpleDateFormat("yyMMddHHmm", Locale.getDefault())
            val date = try {
                formatter.parse(bookmarks.value[index].bookmarkTime.toString())
            } catch (e: Exception) {
                null
            }

            SelectionListItem(
                title = bookmarks.value[index].bookmarkName,
                multilineTitle = true,
                description = "Surah ${bookmarks.value[index].surahNo}, Verse ${bookmarks.value[index].verseNo}, Bookmarked at $date",
                icon = Icons.Default.Delete,
                onIconClick = {
                    coroutineScope.launch {
                        DataSource.database.bookmarksDao.deleteBookmark(
                            bookmarks.value[index]
                        )

                        bookmarks.value = DataSource.database.bookmarksDao.getAllBookmarks()
                    }
                },
                onClick = {
                    coroutineScope.launch {
                        context.dataStore.edit { preferences ->
                            preferences[stringPreferencesKey("lastReadSurah")] =
                                bookmarks.value[index].surahNo.toString()
                            preferences[stringPreferencesKey("lastReadVerse")] =
                                bookmarks.value[index].verseNo.toString()
                        }
                        DataSource.navController.navigate("quran")
                    }
                }
            )
        }

        if (bookmarks.value.isEmpty()) {
            item {
                Text(
                    text = "No bookmarks",
                    color = Color.White,
                    fontFamily = DataSource.alKalamiFont,
                    fontSize = 30.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}