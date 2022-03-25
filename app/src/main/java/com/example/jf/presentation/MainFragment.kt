package com.example.jf.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.jf.R
import com.example.jf.databinding.FragmentMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class MainFragment : Fragment(R.layout.fragment_main) {
    private lateinit var binding: FragmentMainBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = Firebase.auth
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentMainBinding.bind(view)

        val currentUser = auth.currentUser

        with(binding) {
            if (currentUser != null)
                btnExit.visibility = View.VISIBLE
            else
                btnLogin.visibility = View.VISIBLE

            btnLogin.setOnClickListener {
                setLoginFragment()
            }

            btnExit.setOnClickListener {
                Firebase.auth.signOut()
                btnExit.visibility = View.GONE
                btnLogin.visibility = View.VISIBLE
            }
        }
    }

    private fun setLoginFragment() {
        val loginFragment = LoginFragment()
        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.container, loginFragment)
            ?.addToBackStack("login")
            ?.commit()
    }
}
