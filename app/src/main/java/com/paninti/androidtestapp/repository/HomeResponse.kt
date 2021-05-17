package com.paninti.androidtestapp.repository

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class RegionedResponse(
    val regionName: String,
    val data: ArrayList<AllResponse>,
    var expand : Boolean = false
)

@Parcelize
data class AllResponse(
    val name: String?,
    val topLevelDomain: List<String>?,
    val alpha2Code: String?,
    val alpha3Code: String?,
    val callingCodes: List<String>?,
    val capital: String?,
    val altSpellings: List<String>?,
    val region: String?,
    val subregion: String?,
    val population: Int?,
    val latlng: List<Float>?,
    val demonym: String?,
    val area: Float?,
    val gini: Float?,
    val timezones: List<String>?,
    val borders: List<String>?,
    val nativeName: String?,
    val numericCode: String?,
    val currencies: ArrayList<Currencies>?,
    val languages: ArrayList<Languages>?,
    val translations: Translations?,
    val flag: String?,
    val regionalBlocs: ArrayList<RegionalBlocs>?,
    val cioc: String?
) : Parcelable

@Parcelize
data class Currencies(
    val code: String,
    val name: String,
    val symbol: String
) : Parcelable

@Parcelize
data class Languages(
    val iso639_1: String,
    val iso639_2: String,
    val name: String,
    val nativeName: String
) : Parcelable

@Parcelize
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
) : Parcelable

@Parcelize
data class RegionalBlocs(
    val blocs: ArrayList<Blocs>
) : Parcelable

@Parcelize
data class Blocs(
    val acronym: String,
    val name: String,
    val otherAcronyms: List<String>?,
    val otherNames: List<String>?
) : Parcelable

@Parcelize
data class FilterType(
    val filterName: String,
    var active : Boolean = false
) : Parcelable

@Parcelize
data class FilterList(
    val data: ArrayList<FilterType>
) : Parcelable