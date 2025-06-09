package com.yousufjamil.myjquran.accessories.jsondecode

import android.content.Context
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class SurahChapters(
    val id: Int,
    val name: String,
    val transliteration: String,
    val translation: String,
    val type: String,
    @SerialName("total_verses") val totalVerses: Int,
    val link: String
)

fun getJsonDecodedChapters(context: Context, langCode: String): List<SurahChapters> {
    val fileName = "text/$langCode/chapters${langCode.replaceFirstChar { it.uppercase() }}.json"
    val jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
    val chapters = Json.decodeFromString<List<SurahChapters>>(jsonString)
    return chapters
}