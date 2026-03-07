package com.lidev.mycountriesapp.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lidev.mycountriesapp.data.database.entity.CountryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CountryDao {
    @Query("SELECT * FROM countries")
    fun getCountries(): Flow<List<CountryEntity>>

    @Query("SELECT * FROM countries WHERE code = :code")
    suspend fun getCountryByCode(code: String): CountryEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCountries(countries: List<CountryEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCountry(country: CountryEntity)

    @Query("DELETE FROM countries")
    suspend fun deleteAllCountries()
}
