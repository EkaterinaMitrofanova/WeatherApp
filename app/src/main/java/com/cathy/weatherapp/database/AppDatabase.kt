package com.cathy.weatherapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.cathy.weatherapp.model.search.City

@Database(entities = [City::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase(){

    abstract fun weatherDao(): WeatherDao

    companion object {
        private var INSTANCE: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java, "database")
                    .build()
            }
            return INSTANCE as AppDatabase
        }
    }
}