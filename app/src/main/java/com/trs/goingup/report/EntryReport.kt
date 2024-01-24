package com.trs.goingup.report

import com.trs.goingup.data.AppDatabase
import com.trs.goingup.data.Entry
import com.trs.goingup.data.EntrySummary
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream
import javax.mail.internet.InternetAddress
import javax.mail.util.ByteArrayDataSource

class EntryReport {
    companion object {

        suspend fun sendReport(db: AppDatabase) {

            val summaryCsv = ByteArrayDataSource(entrySummaryCsv(db.entryDao().getAllSummary()), "text/csv")
            val entriesCsv = ByteArrayDataSource(entryCsv(db.entryDao().getAll()), "text/csv")

            EmailUtil.sendEmail(
                toEmail = InternetAddress(""),
                subject = "Going Up - Report",
                attachments = listOf(
                    Pair("summary.csv", summaryCsv),
                    Pair("entries.csv", entriesCsv)
                )
            )
        }

        private fun entryCsv(entries: List<Entry>): ByteArray {
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

            val os = ByteArrayOutputStream().apply { writeCsv(entries) }
            return os.toByteArray()
        }

        private fun entrySummaryCsv(entries: List<EntrySummary>): ByteArray {
            fun OutputStream.writeCsv(entries: List<EntrySummary>) {
                val writer = bufferedWriter()
                writer.write(""""Value","Date","Count","First","Last"""")
                writer.newLine()
                entries.forEach {
                    writer.write("\"${it.value}\",")
                    writer.write("\"${it.date}\",")
                    writer.write("\"${it.count}\",")
                    writer.write("\"${it.first}\",")
                    writer.write("\"${it.last}\"")

                    writer.newLine()
                }
                writer.flush()
            }

            val os = ByteArrayOutputStream().apply { writeCsv(entries) }
            return os.toByteArray()
        }
    }
}
