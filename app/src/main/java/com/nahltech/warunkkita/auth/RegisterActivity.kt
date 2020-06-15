package com.nahltech.warunkkita.auth

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.nahltech.warunkkita.R
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {
    private lateinit var authViewModel: AuthViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        clickIntent()
        toolbarUI()
        setupViewModel()
    }

    private fun clickIntent() {
        login_here.setOnClickListener {
            val moveIntent = Intent(this, LoginActivity::class.java)
            startActivity(moveIntent)
            finish()
        }
    }

    private fun toolbarUI() {
        setSupportActionBar(toolbar_register)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        toolbar_register.setNavigationIcon(R.drawable.ic_back_black)
        toolbar_register.setNavigationOnClickListener { finish() }
    }

    private fun setupViewModel() {
        authViewModel = ViewModelProvider(this).get(AuthViewModel::class.java)
        authViewModel.getState().observe(this, Observer {
            handleUIState(it)
        })
        btn_register.setOnClickListener {

            val name = edt_full_name_register.text.toString().trim()
            val email = edt_email_register.text.toString().trim()
            val phone = edt_phone_register.text.toString().trim()
            val password = edt_password_register.text.toString().trim()
            val retryPassword = edt_retry_password_register.text.toString().trim()

            if (authViewModel.validateRegister(name, email, phone, password, retryPassword)) {
                authViewModel.register(name, email, phone, password, retryPassword)
            }
        }
    }

    private fun handleUIState(it: UserState) {
        when (it) {
            is UserState.IsLoading -> isLoading(it.state)
            is UserState.Error -> {
                toast(it.err)
                isLoading(false)
            }
            is UserState.Failed -> {
                isLoading(false)
                toast(it.message)
            }
            is UserState.ValidateRegister -> {

                it.password?.let {
                    setPasswordError(it)
                }

                it.retryPassword?.let {
                    setRetryPasswordError(it)
                }
            }
            is UserState.ShowToast -> toast(it.message)
            is UserState.IsSuccessRegister -> {
                toast("Daftar berhasil silahkan login")
                startActivity(Intent(this@RegisterActivity, LoginActivity::class.java)).also {
                    finish()
                }
            }
        }
    }

    private fun setEmailError(err : String?){ layout_input_email_register.error = err }

    private fun setPasswordError(err: String?) {
        layout_input_password_register.error = err
    }

    private fun setRetryPasswordError(err: String?) {
        layout_input_retry_password_register.error = err
    }

    private fun isLoading(state: Boolean) {
        if (state) {
            edt_full_name_register.isEnabled = false
            edt_email_register.isEnabled = false
            edt_phone_register.isEnabled = false
            edt_password_register.isEnabled = false
            edt_retry_password_register.isEnabled = false
            btn_register.isEnabled = false
            login_here.isEnabled = false

            btn_register.visibility = View.GONE
            sh_register.visibility = View.VISIBLE
            //loading.isIndeterminate = true
            sh_register.startShimmerAnimation()
        } else {
            sh_register.apply {
                //isIndeterminate = false
                btn_register.visibility = View.VISIBLE
                sh_register.visibility = View.GONE
                sh_register.stopShimmerAnimation()
                //progress = 0
            }
            edt_full_name_register.isEnabled = true
            edt_email_register.isEnabled = true
            edt_phone_register.isEnabled = true
            edt_password_register.isEnabled = true
            edt_retry_password_register.isEnabled = true
            login_here.isEnabled = true
            btn_register.isEnabled = true
        }
    }

    private fun toast(message: String?) = Toast.makeText(this, message, Toast.LENGTH_LONG).show()

}