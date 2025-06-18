package com.yousufjamil.myjquran.accessories

fun getLangCode(lang: String): String {
    return when (lang) {
        "bengali" -> "bn"
        "chinese" -> "zh"
        "english" -> "en"
        "french" -> "fr"
        "indonesian" -> "id"
        "russian" -> "ru"
        "spanish" -> "es"
        "swedish" -> "sv"
        "turkish" -> "tr"
        else -> "ur"
    }
}