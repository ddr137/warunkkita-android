package com.nahltech.warunkkita.auth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.nahltech.warunkkita.MainActivity
import com.nahltech.warunkkita.R
import com.nahltech.warunkkita.utils.Constants
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    private lateinit var authViewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        clickIntent()
        toolbarUI()
        setupViewModel()
        doLogin()
    }

    private fun clickIntent() {
        register_now.setOnClickListener {
            val moveIntent = Intent(this, RegisterActivity::class.java)
            startActivity(moveIntent)
        }
    }

    private fun toolbarUI() {
        setSupportActionBar(toolbar_login)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        toolbar_login.setNavigationIcon(R.drawable.ic_back_black)
        toolbar_login.setNavigationOnClickListener { finish() }
    }

    private fun setupViewModel() {
        authViewModel = ViewModelProvider(this).get(AuthViewModel::class.java)
        authViewModel.getState().observe(this, Observer {
            handleUIState(it)
        })
    }

    private fun doLogin() {
        btn_login.setOnClickListener {
            val inputMethodManager =
                this@LoginActivity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(
                this@LoginActivity.window.decorView.rootView.windowToken, 0
            )

            val emailPhone = edt_email_phone_login.text.toString().trim()
            val password = edt_password_login.text.toString().trim()
            if (authViewModel.validateLogin(emailPhone, password)) {
                authViewModel.login(emailPhone, password)
            }
        }
    }

    private fun handleUIState(it: UserState) {
        when (it) {
            is UserState.Reset -> {
                setPasswordError(null)
            }
            is UserState.Error -> {
                isLoading(false)
                toast(it.err)
            }

            is UserState.ShowToast -> toast(it.message)
            is UserState.Failed -> {
                isLoading(false)
                toast(it.message)
            }
            is UserState.Validate -> {
                it.password?.let {
                    setPasswordError(it)
                }
            }
            is UserState.Success -> {
                Constants.setToken(this@LoginActivity, it.token)
                Constants.setIdUser(this@LoginActivity, it.id)
                toast("Selamat datang ${it.name}")
                startActivity(Intent(this@LoginActivity, MainActivity::class.java)).also {
                    finish()
                }
            }
            is UserState.IsLoading -> isLoading(it.state)
        }
    }

    private fun setPasswordError(err: String?) {
        layout_input_password_login.error = err
    }

    private fun isLoading(state: Boolean) {
        if (state) {
            register_now.isEnabled = false
            btn_login.isEnabled = false

            btn_login.visibility = View.GONE
            sh_login.visibility = View.VISIBLE
            //loading.isIndeterminate = true
            sh_login.startShimmerAnimation()
        } else {
            sh_login.apply {
                //isIndeterminate = false
                btn_login.visibility = View.VISIBLE
                sh_login.visibility = View.GONE
                sh_login.stopShimmerAnimation()
                //progress = 0
            }
            register_now.isEnabled = true
            btn_login.isEnabled = true
        }
    }

    private fun toast(message: String?) = Toast.makeText(this, message, Toast.LENGTH_LONG).show()


}