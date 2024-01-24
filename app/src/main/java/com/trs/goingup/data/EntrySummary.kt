package com.trs.goingup.data

import androidx.room.DatabaseView
import androidx.room.TypeConverters
import java.sql.Date
import java.sql.Time

@DatabaseView(
    viewName = "entry_summary",
    value = """
    SELECT DISTINCT date, value, count(id) as count,
    (   
        SELECT time
        FROM ENTRY as e1
        WHERE e1.date = entry.date AND e1.value = entry.value
        ORDER BY e1.entered ASC
        LIMIT 1
    ) as first,
    (
        SELECT time
        FROM ENTRY as e2
        WHERE e2.date = entry.date AND e2.value = entry.value
        ORDER BY e2.entered DESC
        LIMIT 1
    ) as last
    FROM entry as entry
    GROUP BY value, date
    ORDER BY date, value
 """)
@TypeConverters(Converters::class)
data class EntrySummary(
    val value: String,
    val date: Date,
    val count: Int,
    val first: Time,
    val last: Time
)
