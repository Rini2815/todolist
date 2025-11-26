package com.example.todolist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.todolist.fragments.FavoriteFragment
import com.example.todolist.fragments.HomeFragment
import com.example.todolist.fragments.MenuFragment
import com.example.todolist.fragments.ProfileFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var bottomNav: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNav = findViewById(R.id.bottomNavigationView)

        // Set default fragment (Home)
        loadFragment(HomeFragment())

        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.orderFragment -> {  // Home
                    loadFragment(HomeFragment())
                    true
                }
                R.id.chatFragment -> {   // Favorite
                    loadFragment(FavoriteFragment())
                    true
                }
                R.id.profileFragment -> { // Profile / Account
                    loadFragment(ProfileFragment())
                    true
                }
                else -> false
            }
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.nav_host_fragment, fragment)
            .commit()
    }
}