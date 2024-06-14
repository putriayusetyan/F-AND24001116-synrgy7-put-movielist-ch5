package com.example.challenge5.ui.register

import android.content.Intent
import android.os.Build.VERSION_CODES.R
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.challenge5.R
import com.example.challenge5.ui.main.MainActivity
import com.example.challenge5.utils.DataStoreManager
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {
    private lateinit var dataStoreManager: DataStoreManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        dataStoreManager = DataStoreManager(this)

        val usernameEditText = findViewById<EditText>(R.id.usernameEditText)
        val registerButton = findViewById<Button>(R.id.registerButton)

        registerButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            if (username.isNotEmpty()) {
                GlobalScope.launch {
                    dataStoreManager.saveUser(username, true)
                    startActivity(Intent(this@RegisterActivity, MainActivity::class.java))
                    finish()
                }
            } else {
                Toast.makeText(this, "Please enter a username", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
