package com.moresoft.nosimportashackacom

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.material.button.MaterialButton

class LoginFragment : AppCompatActivity() {

    private lateinit var gso: GoogleSignInOptions
    private lateinit var gsc: GoogleSignInClient
    private lateinit var googleBtn: SignInButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_fragment)

        googleBtn = findViewById<SignInButton>(R.id.sign_in_button)
        googleBtn.setSize(SignInButton.SIZE_STANDARD)

        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        gsc = GoogleSignIn.getClient(this, gso)

        val acct = GoogleSignIn.getLastSignedInAccount(this)
        if (acct != null) {
            navigateToSecondActivity()
        }

        googleBtn.setOnClickListener {
            signIn()
        }
    }

    private fun signIn() {
        val signInIntent = gsc.signInIntent
        startActivityForResult(signInIntent, 1000)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1000) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                task.getResult(ApiException::class.java)
                navigateToSecondActivity()
            } catch (e: ApiException) {
                e.message?.let { Log.d("RESP google exception", it) }
                Toast.makeText(applicationContext, "Something went wrong", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun navigateToSecondActivity() {
        finish()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}
