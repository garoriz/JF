package com.example.jf.features.notes.presentation

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.jf.MainActivity
import com.example.jf.R
import com.example.jf.databinding.FragmentNotesBinding
import com.example.jf.features.notes.domain.model.Note
import com.example.jf.utils.AppViewModelFactory
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

class NotesFragment : Fragment(R.layout.fragment_notes) {
    @Inject
    lateinit var factory: AppViewModelFactory
    private lateinit var binding: FragmentNotesBinding
    private val viewModel: NotesViewModel by viewModels {
        factory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        (activity as MainActivity).appComponent.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentNotesBinding.bind(view)

        initObservers()
        viewModel.onGetNote()
        with(binding) {
            tvSave.setOnClickListener {
                viewModel.onSaveNote(Note(etText.text.toString()))
                ViewCompat.getWindowInsetsController(requireView())
                    ?.hide(WindowInsetsCompat.Type.ime())
                showMessage(R.string.success_save)
            }
        }
    }

    private fun initObservers() {
        viewModel.note.observe(viewLifecycleOwner) { it ->
            it.fold(onSuccess = {
                binding.etText.setText(it?.content ?: "")
            }, onFailure = {
                Log.e("e", it.message.toString())
            })
        }

        viewModel.error.observe(viewLifecycleOwner) {
            Log.e("e", it.message.toString())
        }
    }

    private fun showMessage(stringId: Int) {
        Snackbar.make(
            binding.root,
            stringId,
            Snackbar.LENGTH_LONG
        ).show()
    }
}
