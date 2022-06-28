package com.example.gopos.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.example.gopos.databinding.ActivityLoginBinding
import com.example.gopos.ui.GoPOSViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlin.system.exitProcess

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val viewModel : GoPOSViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setUpClickListeners()
        setContentView(binding.root)
    }

    private fun setUpClickListeners() {
        binding.loginButton.setOnClickListener {
            val login = binding.login.text.toString()
            val password = binding.password.text.toString()
            viewModel.getTokenFromServer(login,password)
            viewModel.getError().observe(this){error ->
                if (error != null) {
                    if(error.isEmpty()){
                        Toast.makeText(this,"Zalogowano!", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, MainActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        startActivity(intent)
                    }else{
                        Toast.makeText(this,error, Toast.LENGTH_SHORT).show()
                        viewModel.clearErrorCollector()
                    }
                }
            }

        }
        binding.exitButton.setOnClickListener {
            exitProcess(0)
        }
    }
}