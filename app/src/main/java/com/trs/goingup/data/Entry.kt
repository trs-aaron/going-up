package com.trs.goingup.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import java.sql.Date
import java.sql.Time
import java.sql.Timestamp

@Entity(tableName = "entry", indices = [Index(value = ["value", "entered", "date"])])
@TypeConverters(Converters::class)
data class Entry(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "value") val value: String,
    @ColumnInfo(name = "date") val date: Date,
    @ColumnInfo(name = "time") val time: Time,
    @ColumnInfo(name = "entered") val entered: Timestamp,
    @ColumnInfo(name = "transmitted") val transmitted: Boolean = false
)
