package com.example.todolist.activities

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.todolist.databinding.ActivityLoginBinding
import com.example.todolist.utils.MyPreferenceManager

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Cek apakah sudah login
        checkLoginStatus()

        setupListeners()
        setupTextWatchers()
    }

    private fun checkLoginStatus() {
        val prefs = MyPreferenceManager(this)
        val username = prefs.getString("logged_in_user", null)

        if (username != null) {
            // Sudah login, langsung ke Home
            navigateToHome()
        }
    }

    private fun setupListeners() {
        // Tombol login awalnya disabled
        binding.btnLogin.isEnabled = false

        // Aktifkan tombol login hanya jika:
        // 1. Checkbox dicentang
        // 2. Username tidak kosong
        // 3. Password tidak kosong
        binding.checkbox.setOnCheckedChangeListener { _, isChecked ->
            validateForm()
        }

        // Tombol login
        binding.btnLogin.setOnClickListener {
            performLogin()
        }

        // Link ke halaman lain (jika ada)
        binding.txtRegister.setOnClickListener {
            // Jika mau ke Register activity
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun setupTextWatchers() {
        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                validateForm()
            }
        }

        binding.edtUsername.addTextChangedListener(textWatcher)
        binding.edtPassword.addTextChangedListener(textWatcher)
    }

    private fun validateForm() {
        val username = binding.edtUsername.text.toString().trim()
        val password = binding.edtPassword.text.toString().trim()
        val isChecked = binding.checkbox.isChecked

        // Enable button hanya jika semua kondisi terpenuhi
        binding.btnLogin.isEnabled = username.isNotEmpty()
                && password.isNotEmpty()
                && isChecked
    }

    private fun performLogin() {
        val username = binding.edtUsername.text.toString().trim()
        val password = binding.edtPassword.text.toString().trim()

        if (username.isEmpty()) {
            binding.edtUsername.error = "Username tidak boleh kosong"
            binding.edtUsername.requestFocus()
            return
        }

        if (password.isEmpty()) {
            binding.edtPassword.error = "Password tidak boleh kosong"
            binding.edtPassword.requestFocus()
            return
        }

        if (!binding.checkbox.isChecked) {
            Toast.makeText(this, "Anda harus menyetujui syarat dan ketentuan", Toast.LENGTH_SHORT).show()
            return
        }

        // Simpan data login
        val prefs = MyPreferenceManager(this)
        prefs.setUsername(username)
        prefs.putString("logged_in_user", username)
        prefs.putString("user_password", password) // Opsional, untuk demo saja

        Toast.makeText(this, "Login berhasil! Selamat datang $username", Toast.LENGTH_SHORT).show()

        // Navigate to Home
        navigateToHome()
    }

    private fun navigateToHome() {
        val intent = Intent(this, HomeActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}