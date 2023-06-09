package com.moresoft.nosimportashackacom

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.moresoft.nosimportashackacom.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var gso: GoogleSignInOptions
    lateinit var gsc: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        gsc = GoogleSignIn.getClient(this, gso)

        var acct = GoogleSignIn.getLastSignedInAccount(this)
        if(acct!=null){
            acct.displayName?.let { Log.d("RESP display name", it) }
            acct.email?.let { Log.d("RESP display name", it) }
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.confidence_users -> {
                    replaceFragment(ConfidenceUsersFragment())
                }
                R.id.panic_button -> {
                    replaceFragment(PanicButtonFragment())
                }
                R.id.zoned_map -> {
                    replaceFragment(ZonedMapFragment())
                }
            }
            true
        }
    }
    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)

        fragmentTransaction.commit()
    }
}