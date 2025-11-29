package com.example.todolist.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.todolist.R
import com.example.todolist.databinding.ActivityHomeBinding
import com.example.todolist.fragments.FavoriteFragment
import com.example.todolist.fragments.HomeFragment
import com.example.todolist.fragments.ProfileFragment

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        replaceFragment(HomeFragment())

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {

                R.id.homeFragment -> replaceFragment(HomeFragment())
                R.id.favoriteFragment -> replaceFragment(FavoriteFragment())
                R.id.profileFragment -> replaceFragment(ProfileFragment())
            }
            true
        }
    }

    private fun replaceFragment(f: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, f)
            .commit()
    }
}