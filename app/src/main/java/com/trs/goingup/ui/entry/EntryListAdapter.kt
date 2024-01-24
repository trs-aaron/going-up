package com.trs.goingup.ui.entry

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.trs.goingup.R
import com.trs.goingup.data.AppDatabase
import com.trs.goingup.data.EntryDetail
import java.text.DateFormat
import java.text.SimpleDateFormat

class EntryListAdapter(private val dataSet: List<EntryDetail>, context: Context) : RecyclerView.Adapter<EntryListAdapter.ViewHolder>() {
    private var _db: AppDatabase? = null
    private val db get() = _db!!

    private var deleteButtonClickHandler: ((e: EntryDetail) -> Unit?)? = null

    init {
        _db = AppDatabase.get(context)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val dateView: TextView
        val valueView: TextView
        val timeView: TextView
        val previousTimeView: TextView
        val initialTimeView: TextView
        val countView: TextView
        val deleteButton: ImageButton

        init {
            dateView = view.findViewById(R.id.list_item_entry_date_view)
            valueView = view.findViewById(R.id.list_item_entry_value_view)
            timeView = view.findViewById(R.id.list_item_entry_time_view)
            previousTimeView = view.findViewById(R.id.list_item_entry_previous_time_view)
            initialTimeView = view.findViewById(R.id.list_item_entry_initial_time_view)
            countView = view.findViewById(R.id.list_item_entry_count_view)
            deleteButton = view.findViewById(R.id.list_item_entry_delete_button)
        }
    }

    fun removeDeleteButtonClickHandler() {
        deleteButtonClickHandler = null
    }

    fun setDeleteButtonClickHandler(handler: (e: EntryDetail) -> Unit?) {
        deleteButtonClickHandler = handler
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.list_item_entry, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val entry: EntryDetail = dataSet[position]
        val isFirstForDate: Boolean = (position == 0 || entry.date != dataSet[position - 1].date)

        viewHolder.dateView.visibility = if (isFirstForDate) View.VISIBLE else View.GONE

        viewHolder.dateView.text = dateFormatter.format(entry.date)
        viewHolder.valueView.text = entry.value
        viewHolder.timeView.text = timeFormatter.format(entry.time)
        viewHolder.previousTimeView.text = entry.previousEntryTime?.let { timeFormatter.format(it) }
        viewHolder.initialTimeView.text = timeFormatter.format(entry.initialEntryTime)
        viewHolder.countView.text = entry.dayCount.toString()

        viewHolder.deleteButton.setOnClickListener { this.handleViewDeleteButtonClick(viewHolder, entry) }
    }

    private fun handleViewDeleteButtonClick(viewHolder: ViewHolder, entry: EntryDetail) {
        this.deleteButtonClickHandler?.invoke(entry)
    }

    override fun getItemCount() = dataSet.size

    companion object {
        private val dateFormatter: DateFormat = SimpleDateFormat("EEEE MMMM dd, yyyy")
        private val timeFormatter: DateFormat = SimpleDateFormat("hh:mm:ss a")
    }
}
