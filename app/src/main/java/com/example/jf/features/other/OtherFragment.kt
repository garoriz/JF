package com.example.jf.features.other

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.jf.R
import com.example.jf.databinding.FragmentOtherBinding

class OtherFragment : Fragment(R.layout.fragment_other) {
    private lateinit var binding: FragmentOtherBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentOtherBinding.bind(view)

        with (binding) {
            btnNewPost.setOnClickListener {
                view.findNavController().navigate(R.id.action_navigation_other_to_newPostFragment)
            }
        }
    }
}
