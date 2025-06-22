package com.yousufjamil.myjquran.featurescreens

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.yousufjamil.myjquran.R
import com.yousufjamil.myjquran.accessories.VerseDisplay
import com.yousufjamil.myjquran.accessories.jsondecode.Verse
import com.yousufjamil.myjquran.data.DataSource
import com.yousufjamil.myjquran.data.dataStore
import com.yousufjamil.myjquran.data.database.BookmarksItemDB
import com.yousufjamil.myjquran.data.database.LanguageItemDB
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

//@Preview(showBackground = true, showSystemUi = true)
@Composable
fun QuranScreen() {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val dataStore = context.dataStore
    val coroutineScope = rememberCoroutineScope()
    val state = rememberLazyListState()

    val lang = remember { mutableStateOf<List<LanguageItemDB>>(emptyList()) }
    val verses = remember { mutableStateOf<List<Verse>>(emptyList()) }
    val lastReadSurah = remember { mutableStateOf("1") }
    val lastReadVerse = remember { mutableStateOf("1") }

    val favFont = remember { mutableStateOf("uthmani") }
    val favFontFile = remember { mutableStateOf(DataSource.uthmaniFont) }

    var audioInitialized by remember { mutableStateOf(false) }
    var playingAudio by remember { mutableStateOf(false) }
    val mediaPlayer = remember { MediaPlayer() }
    var currentAudioUrl =
        "https://download.quranicaudio.com/quran/sa3d_al-ghaamidi/complete/${
            lastReadSurah.value.padStart(
                3,
                '0'
            )
        }.mp3"

    DisposableEffect(lifecycleOwner) {
        mediaPlayer.setAudioAttributes(
            AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .build()
        )
        mediaPlayer.setScreenOnWhilePlaying(true)

        mediaPlayer.setOnPreparedListener { mp ->
            mp.start()
            playingAudio = true
            Toast.makeText(context, "Playing audio...", Toast.LENGTH_SHORT).show()
            println("MediaPlayer prepared and started.")
        }

        mediaPlayer.setOnCompletionListener {
            playingAudio = false
            audioInitialized = false
            mediaPlayer.reset()
            Toast.makeText(context, "Audio finished.", Toast.LENGTH_SHORT).show()
            println("MediaPlayer playback completed.")
        }

        mediaPlayer.setOnErrorListener { mp, what, extra ->
            println("Error playing audio: what=$what, extra=$extra")
            Toast.makeText(context, "Error playing audio: $what", Toast.LENGTH_LONG).show()
            playingAudio = false
            audioInitialized = false
            mp.reset()
            true
        }

        onDispose {
            if (mediaPlayer.isPlaying) {
                mediaPlayer.stop()
            }
            mediaPlayer.release()
            println("MediaPlayer released on dispose")
        }
    }

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
                    lastReadVerse.value = (index + 1).toString()
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
                        currentAudioUrl =
                            "https://download.quranicaudio.com/quran/sa3d_al-ghaamidi/complete/${
                                lastReadSurah.value.padStart(
                                    3,
                                    '0'
                                )
                            }.mp3"

                        try {
                            mediaPlayer.reset()
                            playingAudio = false
                            audioInitialized = false
                            mediaPlayer.setAudioAttributes(
                                AudioAttributes.Builder()
                                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                                    .setUsage(AudioAttributes.USAGE_MEDIA)
                                    .build()
                            )
                            mediaPlayer.setScreenOnWhilePlaying(true)
                        } catch (e: Exception) {
                            println("Exception in NextButton: ${e.message}")
                        }
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

                        currentAudioUrl =
                            "https://download.quranicaudio.com/quran/sa3d_al-ghaamidi/complete/${
                                lastReadSurah.value.padStart(
                                    3,
                                    '0'
                                )
                            }.mp3"

                        try {
                            mediaPlayer.reset()
                            playingAudio = false
                            audioInitialized = false
                            mediaPlayer.setAudioAttributes(
                                AudioAttributes.Builder()
                                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                                    .setUsage(AudioAttributes.USAGE_MEDIA)
                                    .build()
                            )
                            mediaPlayer.setScreenOnWhilePlaying(true)
                        } catch (e: Exception) {
                            println("Exception in PrevButton: ${e.message}")
                        }
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
            Spacer(modifier = Modifier.padding(start = 72.dp))
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

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Image(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Bookmark",
                        colorFilter = ColorFilter.tint(Color.White),
                        modifier = Modifier
                            .clickable {
                                coroutineScope.launch {
                                    DataSource.database.bookmarksDao.insertBookmark(
                                        BookmarksItemDB(
                                            bookmarkName = "Surah ${DataSource.quran[lastReadSurah.value.toInt() - 1].name}, Verse ${lastReadVerse.value}",
                                            bookmarkTime = SimpleDateFormat(
                                                "yyMMddHHmm",
                                                Locale.getDefault()
                                            ).format(Date()).toLong(),
                                            surahNo = lastReadSurah.value.toInt(),
                                            verseNo = lastReadVerse.value.toInt()
                                        )
                                    )

                                    Toast.makeText(context, "Bookmarked", Toast.LENGTH_SHORT).show()
                                }
                            }
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    if (!playingAudio) {
                        Image(
                            imageVector = Icons.Default.PlayArrow,
                            contentDescription = "Play Audio",
                            colorFilter = ColorFilter.tint(Color.White),
                            modifier = Modifier
                                .size(40.dp)
                                .clickable {
                                    try {
                                        if (mediaPlayer.isPlaying) {
                                            println("Play clicked but already playing. No action.")
                                            Toast.makeText(
                                                context,
                                                "Audio is already playing.",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        } else if (audioInitialized && mediaPlayer.currentPosition > 0) {
                                            mediaPlayer.start()
                                            playingAudio = true
                                            Toast.makeText(
                                                context,
                                                "Resuming audio",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                            println("Resuming playback from paused state.")
                                        } else {
                                            Toast.makeText(
                                                context,
                                                "Preparing audio for Surah ${lastReadSurah.value}",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                            mediaPlayer.reset()
                                            mediaPlayer.setDataSource(currentAudioUrl)
                                            mediaPlayer.prepareAsync()
                                            audioInitialized = true
                                            println("Starting new playback: $currentAudioUrl")
                                        }
                                    } catch (e: Exception) {
                                        Toast.makeText(
                                            context,
                                            "Error setting up audio: " + e.message,
                                            Toast.LENGTH_LONG
                                        ).show()
                                        println("Exception in Play/Resume: ${e.message}")
                                        playingAudio = false
                                        audioInitialized = false
                                        mediaPlayer.reset()
                                    }
                                }
                        )
                    } else {
                        Image(
                            painter = painterResource(R.drawable.pause),
                            contentDescription = "Pause Audio",
                            colorFilter = ColorFilter.tint(Color.White),
                            modifier = Modifier
                                .size(40.dp)
                                .clickable {
                                    try {
                                        if (mediaPlayer.isPlaying) {
                                            mediaPlayer.pause()
                                            playingAudio = false
                                            Toast.makeText(
                                                context,
                                                "Audio paused",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                            println("Pausing playback.")
                                        }
                                    } catch (e: Exception) {
                                        Toast.makeText(
                                            context,
                                            "Error pausing audio: " + e.message,
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        println("Exception in Pause: ${e.message}")
                                    }
                                }
                        )
                    }
                }

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
