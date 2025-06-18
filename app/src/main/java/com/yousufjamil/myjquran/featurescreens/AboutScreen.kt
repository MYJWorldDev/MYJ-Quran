package com.yousufjamil.myjquran.featurescreens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import com.yousufjamil.myjquran.accessories.SelectionListItem
import com.yousufjamil.myjquran.data.DataSource

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AboutScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF607D94))
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val context = LocalContext.current

        Text(
            text = "Sources",
            color = Color.White,
            fontFamily = DataSource.alKalamiFont,
            fontSize = 60.sp,
            textAlign = TextAlign.Center
        )

        SelectionListItem(
            title = "Quran data © risan/quran-json, licensed under the Creative Commons Attribution-ShareAlike 4.0 International License (CC BY-SA 4.0) License.",
            description = "Licensing info",
            multilineTitle = true,
            onClick = {
                val intent = Intent(Intent.ACTION_VIEW, "https://github.com/risan/quran-json".toUri())
                context.startActivity(intent)
            }
        )

        SelectionListItem(
            title = "The Uthmani Quran text is from The Noble Qur'an Encyclopedia.",
            description = "Quran text info",
            multilineTitle = true,
            onClick = {
                val intent = Intent(Intent.ACTION_VIEW, "https://quranenc.com/en/home".toUri())
                context.startActivity(intent)
            }
        )

        SelectionListItem(
            title = "The English transliteration is from tanzil.net.",
            description = "Translation info",
            multilineTitle = true,
            onClick = {
                val intent = Intent(Intent.ACTION_VIEW, "https://tanzil.net/trans/en.transliteration".toUri())
                context.startActivity(intent)
            }
        )

        SelectionListItem(
            title = "The Bengali translation is authored by Muhiuddin Khan, and it's sourced from tanzil.net.",
            description = "Translation info",
            multilineTitle = true,
            onClick = {
                val intent = Intent(Intent.ACTION_VIEW, "https://tanzil.net/trans/bn.bengali".toUri())
                context.startActivity(intent)
            }
        )

        SelectionListItem(
            title = "The English translation is authored by Umm Muhammad (Saheeh International), and it's sourced from tanzil.net.",
            description = "Translation info",
            multilineTitle = true,
            onClick = {
                val intent = Intent(Intent.ACTION_VIEW, "https://tanzil.net/trans/en.sahih".toUri())
                context.startActivity(intent)
            }
        )

        SelectionListItem(
            title = "The Spanish translation is authored by Muhammad Isa García, and it's sourced from tanzil.net.",
            description = "Translation info",
            multilineTitle = true,
            onClick = {
                val intent = Intent(Intent.ACTION_VIEW, "https://tanzil.net/trans/es.garcia".toUri())
                context.startActivity(intent)
            }
        )

        SelectionListItem(
            title = "The French translation is authored by Muhammad Hamidullah, and it's sourced from tanzil.net..",
            description = "Translation info",
            multilineTitle = true,
            onClick = {
                val intent = Intent(Intent.ACTION_VIEW, "https://tanzil.net/trans/fr.hamidullah".toUri())
                context.startActivity(intent)
            }
        )

        SelectionListItem(
            title = "The Indonesian translation is authored by Indonesian Islamic Affairs Ministry, and it's sourced from The Noble Qur'an Encyclopedia.",
            description = "Translation info",
            multilineTitle = true,
            onClick = {
                val intent = Intent(Intent.ACTION_VIEW, "https://quranenc.com/en/browse/indonesian_affairs".toUri())
                context.startActivity(intent)
            }
        )

        SelectionListItem(
            title = "The Russian translation is authored by Elmir Kuliev, and it's sourced from tanzil.net.",
            description = "Translation info",
            multilineTitle = true,
            onClick = {
                val intent = Intent(Intent.ACTION_VIEW, "https://tanzil.net/trans/ru.kuliev".toUri())
                context.startActivity(intent)
            }
        )

        SelectionListItem(
            title = "he Swedish translation is authored by Knut Bernström, and it's sourced from tanzil.net.",
            description = "Translation info",
            multilineTitle = true,
            onClick = {
                val intent = Intent(Intent.ACTION_VIEW, "https://tanzil.net/trans/sv.bernstrom".toUri())
                context.startActivity(intent)
            }
        )

        SelectionListItem(
            title = "The Turkish translation is authored by Turkish Directorate of Religious Affairs, and it's sourced from tanzil.net.",
            description = "Translation info",
            multilineTitle = true,
            onClick = {
                val intent = Intent(Intent.ACTION_VIEW, "https://tanzil.net/trans/tr.diyanet".toUri())
                context.startActivity(intent)
            }
        )

        SelectionListItem(
            title = "The Urdu translation is authored by Abul A'la Maududi, and it's sourced from tanzil.net.",
            description = "Translation info",
            multilineTitle = true,
            onClick = {
                val intent = Intent(Intent.ACTION_VIEW, "https://tanzil.net/trans/ur.maududi".toUri())
                context.startActivity(intent)
            }
        )

        SelectionListItem(
            title = "The Chinese translation is authored by Muhammad Makin, and it's sourced from The Noble Qur'an Encyclopedia.",
            description = "Translation info",
            multilineTitle = true,
            onClick = {
                val intent = Intent(Intent.ACTION_VIEW, "https://quranenc.com/en/browse/chinese_makin".toUri())
                context.startActivity(intent)
            }
        )
    }
}