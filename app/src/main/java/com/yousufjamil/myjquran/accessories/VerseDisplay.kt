package com.yousufjamil.myjquran.accessories

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yousufjamil.myjquran.data.DataSource

@Composable
fun VerseDisplay(
    surahNumber: Int,
    verseNumber: Int,
    verse: String,
    verseTranslation: String,
    font: FontFamily
) {
    val context = LocalContext.current
    val clipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF607D94))
            .padding(8.dp)
            .shadow(
                elevation = 8.dp,
                spotColor = Color.Black
            )
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xFF151C21))
            .clickable {
                val clipData = ClipData.newPlainText("Copied Verse - MYJ Qur'an", "Surah Number: $surahNumber, \nVerse Number: $verseNumber, \nVerse: $verse, \nTranslation: $verseTranslation")
                clipboardManager.setPrimaryClip(clipData)
                Toast.makeText(context, "Verse copied to clipboard", Toast.LENGTH_SHORT).show()
            }
            .padding(16.dp)
    ) {
        Row {
            Text(
                text = "$verse ${if (verseNumber == 0) "" else engToArabicNum(verseNumber.toString())}",
                fontFamily = font,
                fontSize = 30.sp,
                lineHeight = 50.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                textAlign = TextAlign.End,
                color = Color.White
            )
        }

        Text(
            text = "${if (verseNumber == 0) "" else "$verseNumber. "}$verseTranslation",
            fontSize = 25.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            textAlign = TextAlign.Start,
            color = Color.White
        )
    }
}

fun engToArabicNum(number: String): String {
    val arabicNumbers = arrayOf("٠", "١", "٢", "٣", "٤", "٥", "٦", "٧", "٨", "٩")
    var result = ""
    for (i in number.toCharArray()) {
        result += arabicNumbers[i.toString().toInt()]
    }
    return result
}

@Preview(showBackground = true)
@Composable
fun VerseDisplayPreview() {
    VerseDisplay(
        surahNumber = 53,
        verseNumber = 48,
        verse = "وَاَنَّهٗ هُوَ اَغۡنٰى وَ اَقۡنٰىۙ",
        verseTranslation = "And He is the One Who enriches and impoverishes.",
        font = DataSource.uthmaniFont
    )
}