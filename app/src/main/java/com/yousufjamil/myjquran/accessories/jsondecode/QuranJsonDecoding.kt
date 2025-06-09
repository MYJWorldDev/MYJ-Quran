package com.yousufjamil.myjquran.accessories.jsondecode

import android.content.Context
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class SurahQuran(
    val id: Int,
    val name: String,
    val transliteration: String,
    val translation: String,
    val type: String,
    @SerialName("total_verses") val totalVerses: Int,
    val verses: List<Verse>
)

@Serializable
data class Verse(
    val id: Int,
    val text: String,
    val translation: String
)

fun getJsonDecodedQuran(context: Context, langCode: String): List<SurahQuran> {
    val fileName = "text/$langCode/quran${langCode.replaceFirstChar { it.uppercase() }}.json"
    val jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
    val chapters = Json.decodeFromString<List<SurahQuran>>(jsonString)
    return chapters
}