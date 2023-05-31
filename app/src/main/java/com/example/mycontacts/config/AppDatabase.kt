package com.example.mycontacts.config

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mycontacts.dao.CardDao
import com.example.mycontacts.models.Card


@Database(entities = [Card::class], version = 1)
abstract class AppDatabase :RoomDatabase(){
    abstract fun noteDao(): CardDao
}