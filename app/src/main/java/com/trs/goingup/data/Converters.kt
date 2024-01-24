package com.trs.goingup.data

import androidx.room.TypeConverter
import java.sql.Date
import java.sql.Time
import java.sql.Timestamp
import java.text.DateFormat
import java.text.SimpleDateFormat

class Converters {
    private val dateFormatter: DateFormat = SimpleDateFormat("yyyy-MM-dd")
    private val timeFormatter: DateFormat = SimpleDateFormat("HH:mm:ss")


    @TypeConverter
    fun dateFromString(value: String?): Date? {
        return value?.let { dateFormatter.parse(value)?.let { Date(it.time) } }
    }

    @TypeConverter
    fun dateToString(value: Date?): String? {
        return value?.let { dateFormatter.format(it) }
    }

    @TypeConverter
    fun timeFromString(value: String?): Time? {
        return value?.let { timeFormatter.parse(value)?.let { Time(it.time) } }
    }

    @TypeConverter
    fun timeToString(value: Time?): String? {
        return value?.let { timeFormatter.format(it) }
    }

    @TypeConverter
    fun timestampFromMilliseconds(value: Long?): Timestamp? {
        return value?.let { Timestamp(value) }
    }

    @TypeConverter
    fun timestampToMilliseconds(value: Timestamp?): Long? {
        return value?.time?.toLong()
    }
}
