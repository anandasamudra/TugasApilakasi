package com.mylist.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.mylist.R
import com.mylist.view.Login

class Profile : Fragment() {

    private lateinit var namaTextView: TextView
    private lateinit var emailTextView: TextView
    private lateinit var logoutButton: Button
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        namaTextView = view.findViewById(R.id.nama_textview)
        emailTextView = view.findViewById(R.id.email_textview)
        logoutButton = view.findViewById(R.id.logout)

        firebaseAuth = FirebaseAuth.getInstance()

        logoutButton.setOnClickListener {
            val googleSignInClient = GoogleSignIn.getClient(requireContext(), GoogleSignInOptions.DEFAULT_SIGN_IN)
            googleSignInClient.revokeAccess()
                .addOnCompleteListener(requireActivity()) { task ->
                    if (task.isSuccessful) {
                        val intent = Intent(activity, Login::class.java)
                        startActivity(intent)
                        activity?.finish()
                    }
                }

        }

        val currentUser: FirebaseUser? = firebaseAuth.currentUser

        currentUser?.let {
            val displayName = it.displayName
            val email = it.email

            namaTextView.text = "$displayName"
            emailTextView.text = "$email"
        }

        return view
    }
}
