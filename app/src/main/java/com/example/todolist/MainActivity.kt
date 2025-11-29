package com.example.todolist

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.todolist.databinding.ActivityMainBinding
import com.example.todolist.fragments.FavoriteFragment
import com.example.todolist.fragments.HomeFragment
import com.example.todolist.fragments.ProfileFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Fragment default: Home
        replaceFragment(HomeFragment())

        // Bottom Navigation Listener
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.homeFragment -> replaceFragment(HomeFragment())
                R.id.favoriteFragment -> replaceFragment(FavoriteFragment())
                R.id.profileFragment -> replaceFragment(ProfileFragment())
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }
}