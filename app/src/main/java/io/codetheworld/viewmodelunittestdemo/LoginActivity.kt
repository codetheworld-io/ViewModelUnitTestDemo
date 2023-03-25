package io.codetheworld.viewmodelunittestdemo

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {
    private lateinit var viewModel: LoginViewModel
    private lateinit var usernameTextView: TextView
    private lateinit var passwordTextView: TextView
    private lateinit var messageTextView: TextView
    private lateinit var loginButton: Button
    private lateinit var processBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        usernameTextView = findViewById(R.id.text_username)
        passwordTextView = findViewById(R.id.text_password)
        messageTextView = findViewById(R.id.text_message)
        loginButton = findViewById(R.id.button_login)
        processBar = findViewById(R.id.progress_bar)

        viewModel = LoginViewModel(UserRepository(), SessionManager())

        viewModel.viewState.observe(this) {
            updateUI(it)
        }

        loginButton.setOnClickListener {
            viewModel.login(usernameTextView.text.toString(), passwordTextView.text.toString())
        }
    }

    private fun updateUI(viewState: LoginViewModel.ViewState) {
        when (viewState) {
            is LoginViewModel.ViewState.Loading -> {
                processBar.visibility = View.VISIBLE
                messageTextView.visibility = View.INVISIBLE
            }
            is LoginViewModel.ViewState.Content -> {
                processBar.visibility = View.GONE
                finish()
                startActivity(Intent(this, MainActivity::class.java))
            }
            is LoginViewModel.ViewState.Error -> {
                processBar.visibility = View.GONE
                messageTextView.text = viewState.message
                messageTextView.visibility = View.VISIBLE
            }
        }
    }
}