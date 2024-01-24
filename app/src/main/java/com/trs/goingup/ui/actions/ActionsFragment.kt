package com.trs.goingup.ui.actions

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.trs.goingup.R
import com.trs.goingup.data.AppDatabase
import com.trs.goingup.report.EntryReport
import com.trs.goingup.databinding.FragmentActionsBinding
import com.trs.goingup.report.EmailUtil
import com.trs.goingup.ui.dialog.DialogUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ActionsFragment : Fragment() {

    private var _applicationContext: Context? = null
    private var _binding: FragmentActionsBinding? = null
    private var _db: AppDatabase? = null
    private var _viewModel: ActionsViewModel? = null

    private val applicationContext get() = _applicationContext!!
    private val binding get() = _binding!!
    private val db get() = _db!!
    private val viewModel get() = _viewModel!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentActionsBinding.inflate(inflater, container, false)
        _applicationContext = activity?.applicationContext
        _db = AppDatabase.get(applicationContext)
        _viewModel = ViewModelProvider(this)[ActionsViewModel::class.java]

        initListeners()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun clearEntries() {
        lifecycleScope.launch(Dispatchers.IO) {
            db.entryDao().removeAll()
        }
    }

    private fun deleteEntries() {
        lifecycleScope.launch(Dispatchers.IO) {
            db.entryDao().deleteAll()
        }
    }

    private fun sendReport() {
        lifecycleScope.launch(Dispatchers.IO) {
            EntryReport.sendReport(db)
        }
    }

    private fun handleButton1Click() {
        context?.let {
            DialogUtil.openConfirmationDialog(
                it,
                resources.getString(R.string.action_1_dialog_message),
                resources.getString(R.string.action_1_dialog_title),
                { sendReport() }
            )
        }
    }

    private fun handleButton2Click() {
        context?.let {
            DialogUtil.openConfirmationDialog(
                it,
                resources.getString(R.string.action_2_dialog_message),
                resources.getString(R.string.action_2_dialog_title),
                {}
            )
        }
    }

    private fun handleButton3Click() {
        context?.let {
            DialogUtil.openConfirmationDialog(
                it,
                resources.getString(R.string.action_3_dialog_message),
                resources.getString(R.string.action_3_dialog_title),
                { deleteEntries() }
            )
        }
    }

    private fun handleButton4Click() {
        context?.let {
            DialogUtil.openConfirmationDialog(
                it,
                resources.getString(R.string.action_4_dialog_message),
                resources.getString(R.string.action_4_dialog_title),
                { clearEntries() }
            )
        }
    }

    private fun initListeners() {
        binding.actionsButton1.setOnClickListener { this.handleButton1Click() }
        binding.actionsButton2.setOnClickListener { this.handleButton2Click() }
        binding.actionsButton3.setOnClickListener { this.handleButton3Click() }
        binding.actionsButton4.setOnClickListener { this.handleButton4Click() }
    }
}
