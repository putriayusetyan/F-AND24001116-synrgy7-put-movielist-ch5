package com.example.challenge5.ui.login

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.challenge5.R
import com.example.challenge5.ui.main.MainActivity
import com.example.challenge5.utils.DataStoreManager
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    private lateinit var dataStoreManager: DataStoreManager

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        dataStoreManager = DataStoreManager(this)

        val usernameEditText = findViewById<EditText>(R.id.usernameEditText)
        val loginButton = findViewById<Button>(R.id.loginButton)

        loginButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            GlobalScope.launch {
                dataStoreManager.saveUser(username, true)
                startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                finish()
            }
        }

        GlobalScope.launch {
            dataStoreManager.loggedIn.collect { loggedIn ->
                if (loggedIn) {
                    startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                    finish()
                }
            }
        }
    }
}
