package com.yousufjamil.myjquran

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yousufjamil.myjquran.accessories.SelectionListItem
import com.yousufjamil.myjquran.accessories.SelectionListItemData
import com.yousufjamil.myjquran.data.DataSource

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreen() {
    val navController = DataSource.navController

    val homeScreenItemList = listOf(
        SelectionListItemData(
            title = "Resume",
            description = "Resume where you left off.",
            icon = Icons.Default.DateRange,
            onClick = {
                navController.navigate("quran")
            }
        ),
        SelectionListItemData(
            title = "Bookmarks",
            description = "View your bookmarks.",
            icon = Icons.Default.Star,
            onClick = {
                navController.navigate("bookmarks")
            }
        ),
        SelectionListItemData(
            title = "Surah List",
            description = "Choose surah to read.",
            icon = Icons.AutoMirrored.Default.List,
            onClick = {
                navController.navigate("surahSelect")
            }
        ),
        SelectionListItemData(
            title = "Settings",
            description = "Change your settings.",
            icon = Icons.Default.Settings,
            onClick = {
                navController.navigate("settings")
            }
        ),
        SelectionListItemData(
            title = "Info",
            description = "Info about sources.",
            icon = Icons.Default.Info,
            onClick = {
                navController.navigate("about")
            }
        )
    )

    Column (
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF607D94))
    ) {

        Text(
            text = "القرآن الكريم",
            color = Color.White,
            modifier = Modifier.padding(bottom = 30.dp),
            fontFamily = DataSource.alKalamiFont,
            fontSize = 50.sp
        )

        homeScreenItemList.forEach {
            SelectionListItem(
                title = it.title,
                description = it.description,
                icon = it.icon,
                onClick = it.onClick
            )
        }
    }
}