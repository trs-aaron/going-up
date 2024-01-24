package com.trs.goingup.ui.entry

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.trs.goingup.data.AppDatabase
import com.trs.goingup.data.EntryDetail
import com.trs.goingup.databinding.FragmentEntryListBinding
import com.trs.goingup.ui.dialog.DialogUtil
import kotlinx.coroutines.launch

class EntryListFragment : Fragment() {

    private var _applicationContext: Context? = null
    private var _binding: FragmentEntryListBinding? = null
    private var _db: AppDatabase? = null
    private var _viewModel: EntryListViewModel? = null

    private val applicationContext get() = _applicationContext!!
    private val binding get() = _binding!!
    private val db get() = _db!!
    private val viewModel get() = _viewModel!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEntryListBinding.inflate(inflater, container, false)
        _applicationContext = activity?.applicationContext
        _db = AppDatabase.get(applicationContext)
        _viewModel = ViewModelProvider(this)[EntryListViewModel::class.java]

        initObservers()
        loadEntries()

        return binding.root
    }

    private fun deleteEntry(entry: EntryDetail) {
        lifecycleScope.launch {
            db.entryDao().deleteById(entry.id)
            loadEntries()
        }
    }

    private fun loadEntries() {
        lifecycleScope.launch {
            viewModel.entries.apply {
                value = db.entryDao().getAllDetail()
            }
        }
    }

    private fun handleEntryDeleteButtonClick(entry: EntryDetail) {
        context?.let {
            DialogUtil.openConfirmationDialog(
                it,
                "Are you sure you want to delete?",
                "Delete",
                { deleteEntry(entry) },
                {}
            )
        }
    }

    private fun initObservers() {
        val entryListView: RecyclerView = binding.entryList
        viewModel.entries.observe(viewLifecycleOwner) {
            val adapter = EntryListAdapter(it, applicationContext)
            adapter.setDeleteButtonClickHandler { e -> handleEntryDeleteButtonClick(e) }
            entryListView.adapter = adapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
