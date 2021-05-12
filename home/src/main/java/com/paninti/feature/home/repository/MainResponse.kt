package com.paninti.feature.home.repository

data class AllResponse(
    val name: String,
    val topLevelDomain: List<String>,
    val alpha2Code: String,
    val alpha3Code: String,
    val callingCodes: List<String>,
    val capital: String,
    val altSpellings: List<String>,
    val region: String,
    val subRegion: String,
    val latLng: List<Float>,
    val demonym: String,
    val area: Float,
    val gini: Float,
    val timezones: List<String>,
    val borders: List<String>,
    val nativeName: String,
    val numericCode: String,
    val currencies: ArrayList<Currencies>,
    val languages: ArrayList<Languages>,
    val translations: Translations,
    val flag: String,
    val regionalBlocs: RegionalBlocs?,
    val cioc: String
)

data class Currencies(
    val code: String,
    val name: String,
    val symbol: String
)

data class Languages(
    val iso639_1: String,
    val iso639_2: String,
    val name: String,
    val nativeName: String
)

data class Translations(
    val de: String,
    val es: String,
    val fr: String,
    val ja: String,
    val it: String,
    val br: String,
    val pt: String,
    val nl: String,
    val hr: String,
    val fa: String
)

data class RegionalBlocs(
    val blocs: ArrayList<Blocs>
)

data class Blocs(
    val acronym: String,
    val name: String,
    val otherAcronyms: List<String>?,
    val otherNames: List<String>?
)