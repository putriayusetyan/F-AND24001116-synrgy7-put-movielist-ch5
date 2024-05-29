package com.example.challenge5.ui.profile

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.challenge5.R
import com.example.challenge5.ui.login.LoginActivity
import com.example.challenge5.utils.DataStoreManager
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ProfileActivity : AppCompatActivity() {
    private lateinit var dataStoreManager: DataStoreManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        dataStoreManager = DataStoreManager(this)

        val usernameTextView = findViewById<TextView>(R.id.profileUsernameTextView)
        val logoutButton = findViewById<Button>(R.id.logoutButton)

        GlobalScope.launch {
            dataStoreManager.username.collect { username ->
                runOnUiThread {
                    usernameTextView.text = "Username: $username"
                }
            }
        }

        logoutButton.setOnClickListener {
            GlobalScope.launch {
                dataStoreManager.clearUser()
                startActivity(Intent(this@ProfileActivity, LoginActivity::class.java))
                finish()
            }
        }
    }
}
