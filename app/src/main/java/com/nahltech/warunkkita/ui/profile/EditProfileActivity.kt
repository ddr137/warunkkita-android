package com.nahltech.warunkkita.ui.profile

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.nahltech.warunkkita.R
import com.nahltech.warunkkita.data.models.User
import com.nahltech.warunkkita.utils.Constants
import kotlinx.android.synthetic.main.activity_edit_profile.*

class EditProfileActivity : AppCompatActivity() {

    private lateinit var profileViewModel: ProfileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        toolbarUI()
        setupViewModel()
    }

    private fun toolbarUI() {
        setSupportActionBar(toolbar_edit_account)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        toolbar_edit_account.setNavigationIcon(R.drawable.ic_back_white)
        toolbar_edit_account.setNavigationOnClickListener { finish() }
    }

    private fun setupViewModel() {
        profileViewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        doUpdate()
        profileViewModel.fetchProfile(Constants.getToken(applicationContext))
        profileViewModel.getUsers().observe(this, Observer {
            fill(it)
        })

        profileViewModel.getState().observe(this, Observer {
            handleUIState(it)
        })
    }

    private fun fill(user: User) {
        edt_full_name_edit_account.setText(user.name)
        edt_email_edit_account.setText(user.email)
        edt_phone_edit_account.setText(user.phone)
    }

    private fun handleUIState(it: UsersState) {
        when (it) {
            is UsersState.IsLoading -> isLoading(it.state)
            is UsersState.Error -> {
                toast(it.err)
                isLoading(false)
            }
            is UsersState.ShowToast -> toast(it.message)
            is UsersState.ValidateEditAccount -> {
                it.name?.let {
                    setNameError(it)
                }
                it.email?.let{
                    setEmailError(it)
                }
                it.phone?.let{
                    setPhoneError(it)
                }
            }
            is UsersState.Reset -> {
                setNameError(null)
                setEmailError(null)
                setPhoneError(null)
            }
            is UsersState.IsSuccess -> {
                when(it.what){
                    1 -> {
                        toast("Berhasil diupdate")
                        finish()
                    }
                }
            }
        }
    }
    private fun setNameError(err : String?) { layout_input_full_name_edit_account.error = err }
    private fun setEmailError(err : String?) { layout_input_email_edit_account.error = err }
    private fun setPhoneError(err : String?) { layout_input_no_hp_edit_account.error = err }
    private fun isLoading(state: Boolean) {
        if (state) {
            edt_full_name_edit_account.isEnabled = false
            edt_email_edit_account.isEnabled = false
            edt_phone_edit_account.isEnabled = false
            btn_save_edit_account.isEnabled = false

            btn_save_edit_account.visibility = View.GONE
            sh_save_edit_account.visibility = View.VISIBLE
            //loading.isIndeterminate = true
            sh_save_edit_account.startShimmerAnimation()
        } else {
            sh_save_edit_account.apply {
                //isIndeterminate = false
                btn_save_edit_account.visibility = View.VISIBLE
                sh_save_edit_account.visibility = View.GONE
                sh_save_edit_account.stopShimmerAnimation()
                //progress = 0
            }
            edt_full_name_edit_account.isEnabled = true
            edt_email_edit_account.isEnabled = true
            edt_phone_edit_account.isEnabled = true
            btn_save_edit_account.isEnabled = true
        }
    }

    private fun toast(message: String?) = Toast.makeText(this, message, Toast.LENGTH_LONG).show()

    private fun doUpdate() {
        btn_save_edit_account.setOnClickListener {
            val name  = edt_full_name_edit_account.text.toString().trim()
            val email  = edt_email_edit_account.text.toString().trim()
            val phone  = edt_phone_edit_account.text.toString().trim()
            if(profileViewModel.validateEditAccount(name, email, phone)){
                profileViewModel.editAccount(Constants.getToken(this),
                    Constants.getIdUser(this), name, email, phone)
            }
        }
    }
}