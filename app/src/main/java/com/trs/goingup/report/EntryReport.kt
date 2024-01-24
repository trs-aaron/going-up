package com.trs.goingup.report

import com.trs.goingup.data.AppDatabase
import com.trs.goingup.data.Entry
import com.trs.goingup.data.EntrySummary
import java.io.FileOutputStream
import java.io.OutputStream

class EntryReport {
    companion object {

        suspend fun sendReport(db: AppDatabase) {
            entryCsv(db.entryDao().getAll())
        }

        private fun entryCsv(entries: List<Entry>) {
            fun OutputStream.writeCsv(entries: List<Entry>) {
                val writer = bufferedWriter()
                writer.write(""""Value","Date","Time"""")
                writer.newLine()
                entries.forEach {
                    writer.write("\"${it.value}\",")
                    writer.write("\"${it.date}\",")
                    writer.write("\"${it.time}\"")

                    writer.newLine()
                }
                writer.flush()
            }

            FileOutputStream("entries.csv").apply { writeCsv(entries) }
        }

        private fun entrySummaryCsv(entries: List<EntrySummary>) {
            fun OutputStream.writeCsv(entries: List<EntrySummary>) {
                val writer = bufferedWriter()
                writer.write(""""Value","Date","Count","First","Last"""")
                writer.newLine()
                entries.forEach {
                    writer.write("\"${it.value}\",")
                    writer.write("\"${it.date}\",")
                    writer.write("\"${it.count}\",")
                    writer.write("\"${it.firstEntryTime}\",")
                    writer.write("\"${it.lastEntryTime}\"")

                    writer.newLine()
                }
                writer.flush()
            }

            FileOutputStream("entry-summary.csv").apply { writeCsv(entries) }
        }
    }
}
