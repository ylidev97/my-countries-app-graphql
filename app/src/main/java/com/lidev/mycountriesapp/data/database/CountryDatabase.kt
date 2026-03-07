package com.lidev.mycountriesapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.lidev.mycountriesapp.data.database.dao.CountryDao
import com.lidev.mycountriesapp.data.database.entity.CountryEntity

@Database(entities = [CountryEntity::class], version = 1, exportSchema = false)
abstract class CountryDatabase : RoomDatabase() {
    abstract val countryDao: CountryDao
}
