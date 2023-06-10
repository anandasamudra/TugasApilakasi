package com.mylist.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.mylist.MainActivity
import com.mylist.R

class Register : AppCompatActivity() {

    private lateinit var fullNameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var confirmPasswordEditText: EditText
    private lateinit var registerButton: Button
    private lateinit var toLoginTextView: TextView
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(R.layout.activity_register)
        supportActionBar?.hide()

        fullNameEditText = findViewById(R.id.fullname)
        emailEditText = findViewById(R.id.email)
        passwordEditText = findViewById(R.id.pass)
        confirmPasswordEditText = findViewById(R.id.pass2)
        registerButton = findViewById(R.id.register)
        toLoginTextView = findViewById(R.id.tvlogin)
        firebaseAuth = FirebaseAuth.getInstance()

        registerButton.setOnClickListener {
            val fullName = fullNameEditText.text.toString()
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()
            val confirmPassword = confirmPasswordEditText.text.toString()

            if (fullName.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty()) {
                if (password == confirmPassword) {
                    firebaseAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Toast.makeText(this, "Registrasi berhasil", Toast.LENGTH_SHORT).show()
                                val profileUpdates = userProfileChangeRequest {
                                    displayName = fullName
                                }
                                val user = task.result.user
                                user!!.updateProfile(profileUpdates)
                                    .addOnCompleteListener {
                                        startActivity(Intent(this, MainActivity::class.java))
                                    }
                                    .addOnFailureListener { error2 ->
                                        Toast.makeText(this, error2.localizedMessage, Toast.LENGTH_SHORT).show()
                                    }
                            } else {
                                Toast.makeText(this, "Registrasi gagal: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                            }
                        }
                } else {
                    Toast.makeText(this, "Konfirmasi password tidak sesuai", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Mohon lengkapi semua field", Toast.LENGTH_SHORT).show()
            }
        }

        toLoginTextView.setOnClickListener {
            startActivity(Intent(this, Login::class.java))
            finish()
        }
    }
}