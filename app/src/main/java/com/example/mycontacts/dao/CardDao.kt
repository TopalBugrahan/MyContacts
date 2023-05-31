package com.example.mycontacts.dao

import androidx.room.*
import com.example.mycontacts.models.Card

@Dao
interface CardDao {

    @Insert
    fun insert(card:Card ):Long

    @Query("select * from card")
    fun getAll():MutableList<Card>

    @Query("select * from card where cid=:cid")
    fun findById(cid:Int):Card

    @Query("select * from card where name like :name")
    fun searchName(name:String):MutableList<Card>

    @Query("select * from card order by cid desc limit 10;")
    fun lastTenCard():MutableList<Card>

    @Query("select * from card where grop=:group")
    fun findByGroup(group:String):MutableList<Card>

    @Query("DELETE FROM card WHERE cid = :cid")
    fun deleteByCid(cid: Int)

    @Query("UPDATE card SET name=:name,surname=:surname,phoneNumber=:phoneNumber,grop=:group,sex=:sex WHERE cid = :cid")
    fun update(name: String,surname:String,phoneNumber:String,group: String,sex:String, cid: Int)

}