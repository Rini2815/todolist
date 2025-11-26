package com.example.todolist.activities   // sudah diperbaiki, huruf kecil

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.todolist.databinding.ActivityLoginBinding
import com.example.todolist.utils.PreferenceManager
import com.example.todolist.MainActivity       // tambahkan ini
import com.example.todolist.activities.RegisterActivity   // tambahkan ini

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var pref: PreferenceManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        pref = PreferenceManager(this)

        // Jika user sudah login, langsung ke Home
        if (pref.isLoggedIn()) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
            return
        }

        binding.btnLogin.setOnClickListener {
            loginUser()
        }

        binding.txtRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun loginUser() {
        val email = binding.edtEmail.text.toString().trim()
        val password = binding.edtPassword.text.toString().trim()

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Email dan password tidak boleh kosong", Toast.LENGTH_SHORT).show()
            return
        }

        val savedEmail = pref.getEmail()
        val savedPassword = pref.getPassword()

        if (email == savedEmail && password == savedPassword) {
            pref.setLoggedIn(true)
            Toast.makeText(this, "Login berhasil!", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        } else {
            Toast.makeText(this, "Email atau password salah", Toast.LENGTH_SHORT).show()
        }
    }
}
