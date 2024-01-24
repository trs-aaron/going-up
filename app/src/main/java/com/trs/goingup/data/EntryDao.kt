package com.trs.goingup.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import java.sql.Date
import java.sql.Time
import java.sql.Timestamp

@Dao
interface EntryDao {

    @Insert
    suspend fun archive(entry: EntryArchive)

    @Insert
    suspend fun insert(entry: Entry): Long

    @Insert
    suspend fun insert(value: String): Long {
        if (value.isEmpty()) throw Error("Value must not be empty.")

        val now = java.util.Date()
        val entry = Entry(
            value = value,
            date = Date(now.time),
            time = Time(now.time),
            entered = Timestamp(now.time),
            transmitted = false
        )

        val id = insert(entry)
        archive(EntryArchive(id, entry.value, entry.date, entry.time, entry.entered))

        return id
    }

    @Query("DELETE FROM entry")
    suspend fun removeAll()

    @Query("DELETE FROM entry_archive")
    suspend fun removeArchive()

    @Delete
    suspend fun delete(entry: Entry)

    @Delete
    suspend fun deleteAll() {
        removeAll()
        removeArchive()
    }

    @Query("DELETE FROM entry WHERE id = :id")
    suspend fun deleteById(id: Long)

    @Query("SELECT * FROM entry")
    suspend fun getAll(): List<Entry>

    @Query("SELECT * FROM entry_detail")
    suspend fun getAllDetail(): List<EntryDetail>

    //@Query("SELECT * FROM entry_summary")
    //suspend fun getAllSummary(): List<EntrySummary>
}
