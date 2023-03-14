package com.luminate.room_database.data

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.luminate.room_database.data.dao.UserDao
import com.luminate.room_database.data.entity.User
import android.content.Context


@Database(entities = [User::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object
    {
        private var instance : AppDatabase? = null

        fun getInstance(context: Context): AppDatabase
        {
            if (instance == null)
            {
                instance = Room.databaseBuilder(context, AppDatabase::class.java, "app-database" )
                    .fallbackToDestructiveMigration().allowMainThreadQueries().build()
            }
            return instance!!


        }
    }
}