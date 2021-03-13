package com.mustafaokta.besinlerkitabi.service

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mustafaokta.besinlerkitabi.model.Besin

@Database(entities = arrayOf(Besin::class), version =1)
abstract class BesinDatabase: RoomDatabase() {
    abstract fun besinDao() : BesinDAO

    //Singleton tasarım yapıp  farklı threadlerin aynı veriye erişimini engelliyor

    companion object{
        @Volatile private var instance : BesinDatabase? = null
        private val lock = Any()

        //Bu invoke yani ateşleme fonksiyonu ile eğer instance varsa ver yoksa yenisini oluştur ver kontrolü yapıyor
        operator fun invoke(context: Context)= instance?: synchronized(lock){
            instance ?: databaseOlustur(context).also {
                instance=it
            }
        }


        private fun databaseOlustur(context: Context) = Room.databaseBuilder(
                context.applicationContext,
                BesinDatabase:: class.java,
                "besindatabase").build()
    }



}