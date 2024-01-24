package com.trs.goingup.data

import androidx.room.DatabaseView
import androidx.room.TypeConverters
import java.sql.Date
import java.sql.Time
import java.sql.Timestamp

@DatabaseView(
    viewName = "entry_detail",
    value = """
    SELECT id , value, date, time, entered, transmitted,
    (
        SELECT COUNT(*)
        FROM ENTRY as e1
        WHERE e1.date = entry.date AND e1.value = entry.value  AND e1.entered <= entry.entered
    ) as dayCount,
    (   
        SELECT time
        FROM ENTRY as e2
        WHERE e2.date = entry.date AND e2.value = entry.value AND e2.entered <= entry.entered
        ORDER BY e2.entered ASC
        LIMIT 1
    ) as initialEntryTime,
    (
        SELECT time
        FROM ENTRY as e3
        WHERE e3.date = entry.date AND e3.value = entry.value AND e3.entered < entry.entered
        ORDER BY e3.entered DESC
        LIMIT 1
    ) as previousEntryTime
    FROM ENTRY as entry
    ORDER BY entered DESC
 """)
@TypeConverters(Converters::class)
data class EntryDetail(
    val id: Long,
    val value: String,
    val date: Date,
    val time: Time,
    val entered: Timestamp,
    val transmitted: Boolean,
    val dayCount: Int,
    val initialEntryTime: Time,
    val previousEntryTime: Time?
)