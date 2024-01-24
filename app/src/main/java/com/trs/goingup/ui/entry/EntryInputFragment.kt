package com.trs.goingup.ui.entry

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.trs.goingup.data.AppDatabase
import com.trs.goingup.databinding.FragmentEntryInputBinding
import kotlinx.coroutines.launch

class EntryInputFragment : Fragment() {

    enum class NumPadKey {
        CLEAR,
        SUBMIT,
        ZERO,
        ONE,
        TWO,
        THREE,
        FOUR,
        FIVE,
        SIX,
        SEVEN,
        EIGHT,
        NINE
    }

    private var _applicationContext: Context? = null
    private var _binding: FragmentEntryInputBinding? = null
    private var _db: AppDatabase? = null
    private var _viewModel: EntryInputViewModel? = null

    private val applicationContext get() = _applicationContext!!
    private val binding get() = _binding!!
    private val db get() = _db!!
    private val viewModel get() = _viewModel!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEntryInputBinding.inflate(inflater, container, false)
        _applicationContext = activity?.applicationContext
        _db = AppDatabase.get(applicationContext)
        _viewModel = ViewModelProvider(this)[EntryInputViewModel::class.java]

        initObservers()
        initListeners()

        return binding.root
    }

    private fun clear() {
        viewModel.inputVal.apply { value = "" }
    }

    private fun submit() {
        viewModel.inputVal.value?.let { value ->
            createEntry(value)
        }

        clear()
    }

    private fun createEntry(id: String) {
        lifecycleScope.launch {
            db.entryDao().insert(id)
        }
    }

    private fun appendToInput(char: Char) {
        viewModel.inputVal.apply { value += char }
    }

    private fun handleButtonClick(key: NumPadKey) {
        when (key) {
            NumPadKey.ZERO -> appendToInput('0')
            NumPadKey.ONE -> appendToInput('1')
            NumPadKey.TWO -> appendToInput('2')
            NumPadKey.THREE -> appendToInput('3')
            NumPadKey.FOUR -> appendToInput('4')
            NumPadKey.FIVE -> appendToInput('5')
            NumPadKey.SIX -> appendToInput('6')
            NumPadKey.SEVEN -> appendToInput('7')
            NumPadKey.EIGHT -> appendToInput('8')
            NumPadKey.NINE -> appendToInput('9')
            NumPadKey.CLEAR -> clear()
            NumPadKey.SUBMIT -> submit()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initObservers() {
        val textView: TextView = binding.entryInputDisplay
        viewModel.inputVal.observe(viewLifecycleOwner) {
            textView.text = it
        }
    }

    private fun initListeners() {
        binding.entryInputButtonClear.setOnClickListener { this.handleButtonClick(NumPadKey.CLEAR) }
        binding.entryInputButtonSubmit.setOnClickListener { this.handleButtonClick(NumPadKey.SUBMIT) }
        binding.entryInputButton0.setOnClickListener { this.handleButtonClick(NumPadKey.ZERO) }
        binding.entryInputButton1.setOnClickListener { this.handleButtonClick(NumPadKey.ONE) }
        binding.entryInputButton2.setOnClickListener { this.handleButtonClick(NumPadKey.TWO) }
        binding.entryInputButton3.setOnClickListener { this.handleButtonClick(NumPadKey.THREE) }
        binding.entryInputButton4.setOnClickListener { this.handleButtonClick(NumPadKey.FOUR) }
        binding.entryInputButton5.setOnClickListener { this.handleButtonClick(NumPadKey.FIVE) }
        binding.entryInputButton6.setOnClickListener { this.handleButtonClick(NumPadKey.SIX) }
        binding.entryInputButton7.setOnClickListener { this.handleButtonClick(NumPadKey.SEVEN) }
        binding.entryInputButton8.setOnClickListener { this.handleButtonClick(NumPadKey.EIGHT) }
        binding.entryInputButton9.setOnClickListener { this.handleButtonClick(NumPadKey.NINE) }
    }
}
