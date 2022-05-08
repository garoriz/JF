package com.example.jf

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.jf.databinding.ActivityMainBinding
import com.example.jf.di.AppComponent
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


private lateinit var binding: ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var appComponent: AppComponent
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        appComponent = (application as App).appComponent
        appComponent.inject(this)
        super.onCreate(savedInstanceState)
        val currentUser = Firebase.auth.currentUser
        database =
            Firebase.database("https://jf-forum-f415b-default-rtdb.europe-west1.firebasedatabase.app/")
                .reference
        binding = ActivityMainBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }

        if (currentUser != null) {
            val isUnreadChatsRef = database
                .child("users")
                .child(currentUser.uid)
                .child("isUnreadChat")

            val unreadChatsListener = object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val isUnreadChats = dataSnapshot.getValue(Boolean::class.java)
                    if (isUnreadChats == true)
                        binding.badge.visibility = View.VISIBLE
                    else
                        binding.badge.visibility = View.INVISIBLE
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    showMessage(R.string.no_internet)
                }
            }
            isUnreadChatsRef.addValueEventListener(unreadChatsListener)
        }

        val controller = (supportFragmentManager.findFragmentById(R.id.container)
                as NavHostFragment).navController
        val navView = binding.navView



        navView.setupWithNavController(controller)
    }

    private fun showMessage(stringId: Int) {
        Snackbar.make(
            binding.root,
            stringId,
            Snackbar.LENGTH_LONG
        ).show()
    }
}
