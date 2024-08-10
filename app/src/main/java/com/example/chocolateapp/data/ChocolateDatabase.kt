package com.example.chocolateapp.data

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ChocolateEntity::class,
    FormEntity::class, ChocoSetEntity::class,
    FormInSet::class, Order::class, ItemInOrder::class],
    version = 2,
    exportSchema = false)
abstract class ChocolateDatabase : RoomDatabase() {
    abstract fun chocolateDao() : ChocolateDao
    abstract fun formDao() : FormEntityDao
    abstract fun orderDao() : OrderDao
    abstract fun itemInOrderDao() : ItemInOrderDao
    abstract fun chocosetDao() : ChocoSetDao
    abstract fun formInSet() : FormInSetDao

    companion object {
        @Volatile
        private var Instance: ChocolateDatabase? = null

        fun getDatabase(context: Context) : ChocolateDatabase {
            Log.d("hihihi", "hihihi")
            return Instance ?: synchronized (this) {
                Room.databaseBuilder(context, ChocolateDatabase::class.java, "chocolate_db")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also{
                        Instance = it
                    }
            }
        }
    }


}