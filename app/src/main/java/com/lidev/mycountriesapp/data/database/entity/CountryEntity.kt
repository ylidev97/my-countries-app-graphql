package com.lidev.mycountriesapp.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "countries")
data class CountryEntity(
    @PrimaryKey val code: String,
    val name: String,
    val emoji: String,
    val continent: String,
    val capital: String? = null,
    val currency: String? = null,
    val phone: String? = null,
    val languages: String? = null, // Comma separated or JSON
)
