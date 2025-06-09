package com.yousufjamil.myjquran.accessories

import android.content.Context
import com.yousufjamil.myjquran.data.database.LanguageItemDB
import java.util.Locale

fun getDefaultLang(context: Context): LanguageItemDB {
    val supportedLanguages = setOf(
        "english", "urdu", "bengali", "chinese", "french",
        "indonesian", "russian", "spanish", "swedish", "turkish"
    )

    val localeLang = Locale.getDefault().language.lowercase()
    val languageMap = mapOf(
        "en" to "english",
        "ur" to "urdu",
        "bn" to "bengali",
        "zh" to "chinese",
        "fr" to "french",
        "id" to "indonesian",
        "ru" to "russian",
        "es" to "spanish",
        "sv" to "swedish",
        "tr" to "turkish"
    )

    val selectedLang = languageMap[localeLang] ?: "english"
    return LanguageItemDB(
        languageCode = localeLang,
        languageName = selectedLang,
    )
}