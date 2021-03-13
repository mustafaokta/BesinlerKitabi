package com.mustafaokta.besinlerkitabi.service

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.mustafaokta.besinlerkitabi.model.Besin
@Dao
interface BesinDAO {

    //Data Access Object

    @Insert
    suspend fun insertAll(vararg besin : Besin) : List<Long>


    @Query("SELECT * FROM besin")
    suspend fun getAllBesin() :  List<Besin> //Besini liste şeklinde çekmek gerek  besinlistesiFragment için

    @Query("SELECT * FROM Besin WHERE uuid= :besinId")
    suspend fun getBesin(besinId : Int) : Besin

    @Query("DELETE FROM besin")
    suspend fun deleteAllBesin(

    )
 }