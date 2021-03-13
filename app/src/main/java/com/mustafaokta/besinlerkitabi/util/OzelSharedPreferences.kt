package com.mustafaokta.besinlerkitabi.util

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import androidx.core.content.edit

class OzelSharedPreferences {

    companion object{

        private var sharedPreferences:SharedPreferences?=null
        private val lock = Any()
        private val ZAMAN = "zaman"

        @Volatile private var instance : OzelSharedPreferences? =null

         operator fun invoke(context: Context) : OzelSharedPreferences= instance ?: synchronized(lock){
            instance ?: ozelSharedPreferencesYap(context).also {
                instance=it
            }
         }

        private fun ozelSharedPreferencesYap(context: Context): OzelSharedPreferences{
            sharedPreferences=androidx.preference.PreferenceManager.getDefaultSharedPreferences(context)
            return OzelSharedPreferences()

        }
            }
    fun zamaniKaydet(zaman: Long){
    sharedPreferences?.edit(commit = true){
        putLong(ZAMAN, zaman )
    }
    }

    fun zamaniAl()= sharedPreferences?.getLong(ZAMAN,0)
}