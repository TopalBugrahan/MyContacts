package com.example.mycontacts.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "card")
data class Card(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "cid")
    val cid: Int?,
    val name:String?,
    val surname:String?,
    val grop:String?,
    val phoneNumber:String?,
    val sex:String?,

)
