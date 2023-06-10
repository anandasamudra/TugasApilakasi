package com.mylist.view

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.FirebaseDatabase
import com.mylist.MainActivity
import com.mylist.R
import com.mylist.model.Tugas

class Login : AppCompatActivity() {

    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var googleButton: ImageButton
    private lateinit var registerTextView: TextView
    private lateinit var forgetPasswordTextView: TextView
    private lateinit var firebaseAuth: FirebaseAuth
    private var previousUserId: String = ""

    companion object {
        private const val RC_SIGN_IN = 9001
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(R.layout.activity_login)

        supportActionBar?.hide()

        firebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth.addIdTokenListener(FirebaseAuth.IdTokenListener { auth ->
            val currentUser = auth.currentUser
            if (currentUser != null) {
                if (currentUser.uid != previousUserId) {
                    deleteFirebaseDatabase()
                }
            } else {
                // Pengguna telah logout
                // Hapus seluruh database di Firebase
                deleteFirebaseDatabase()
            }
        })

        emailEditText = findViewById(R.id.email)
        passwordEditText = findViewById(R.id.pass)
        loginButton = findViewById(R.id.login)
        googleButton = findViewById(R.id.google)
        registerTextView = findViewById(R.id.tvregister)
        forgetPasswordTextView = findViewById(R.id.forgetpass)

        loginButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                signInWithEmail(email, password)
            } else {
                Toast.makeText(this, "Email dan password harus diisi", Toast.LENGTH_SHORT).show()
            }
        }

        googleButton.setOnClickListener {
            signInWithGoogle()
        }

        registerTextView.setOnClickListener {
            val intent = Intent(this, Register::class.java)
            startActivity(intent)
        }

        forgetPasswordTextView.setOnClickListener {
            val intent = Intent(this, ForgertPassword::class.java)
            startActivity(intent)
        }
    }

    private fun signInWithEmail(email: String, password: String) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, "Login gagal. Periksa kembali email dan password", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun signInWithGoogle() {
        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        val googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions)
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                val idToken = account?.idToken
                val credential = GoogleAuthProvider.getCredential(idToken, null)
                firebaseAuthWithGoogle(credential)
            } catch (e: ApiException) {
                Toast.makeText(this, "Login dengan akun Google gagal", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun firebaseAuthWithGoogle(credential: AuthCredential) {
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, "Login dengan akun Google gagal", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun deleteFirebaseDatabase() {
        val databaseRef = FirebaseDatabase.getInstance().reference
        databaseRef.setValue(null)
    }
}