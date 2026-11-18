package edu.marhuendasamuel.appdummy.data.datasource.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    @TypeConverter
    fun stringListToJson(value: List<String>): String = Gson().toJson(value)

    @TypeConverter
    fun jsonToStringList(value: String): List<String> {
        val type = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(value, type)
    }

    @TypeConverter
    fun dateToTimestamp(date: java.util.Date?): Long? = date?.time

    @TypeConverter
    fun timestampToDate(value: Long?): java.util.Date? = value?.let { java.util.Date(it) }
}