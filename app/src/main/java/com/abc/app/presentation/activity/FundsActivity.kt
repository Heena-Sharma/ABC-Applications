package com.abc.app.presentation.activity

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.abc.app.presentation.fragments.FundsListFragment
import com.abc.app.R
import com.abc.app.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FundsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        supportFragmentManager.commit {
            replace(R.id.nav_host_fragment, FundsListFragment())
            setReorderingAllowed(true)
            addToBackStack(null)
        }
        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }


    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            finish()
        }
    }
}