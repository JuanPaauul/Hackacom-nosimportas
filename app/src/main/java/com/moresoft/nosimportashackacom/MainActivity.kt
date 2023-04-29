package com.moresoft.nosimportashackacom

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.moresoft.nosimportashackacom.ConfidenceUsersFragment
import com.moresoft.nosimportashackacom.PanicButtonFragment
import com.moresoft.nosimportashackacom.ZonedMapFragment
import com.moresoft.nosimportashackacom.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        replaceFragment(ZonedMapFragment())

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