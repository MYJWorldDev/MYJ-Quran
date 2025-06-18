package com.yousufjamil.myjquran.accessories.jsondecode

import android.content.Context
import com.yousufjamil.myjquran.accessories.getLangCode
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

fun getJsonDecodedChapters(context: Context, lang: String): List<SurahChapters> {
    val langCode = getLangCode(lang)
    val fileName = "text/$lang/chapters${langCode.replaceFirstChar { it.uppercase() }}.json"
    val jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
    val chapters = Json.decodeFromString<List<SurahChapters>>(jsonString)
    return chapters
}