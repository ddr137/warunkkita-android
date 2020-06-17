package com.nahltech.warunkkita.ui.profile

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.nahltech.warunkkita.R
import com.nahltech.warunkkita.utils.Constants
import kotlinx.android.synthetic.main.activity_change_password.*

class ChangePasswordActivity : AppCompatActivity() {

    private lateinit var profileViewModel: ProfileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)
        toolbarUI()
        setupViewModel()
    }

    private fun toolbarUI() {
        setSupportActionBar(toolbar_edit_password)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        toolbar_edit_password.setNavigationIcon(R.drawable.ic_back_white)
        toolbar_edit_password.setNavigationOnClickListener { finish() }
    }

    private fun setupViewModel() {
        profileViewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        doUpdate()
        profileViewModel.getState().observe(this, Observer {
            handleUIState(it)
        })
    }

    private fun doUpdate() {
        save_edt_change_password.setOnClickListener {
            val oldPassword  = passwordOld.text.toString().trim()
            val newPassword  = passwordNew.text.toString().trim()
            val repeatPassword  = passwordRepeat.text.toString().trim()
            if (repeatPassword == newPassword){
                if(profileViewModel.validate(oldPassword, newPassword, repeatPassword)){
                    profileViewModel.changePassword(Constants.getToken(this),
                        Constants.getIdUser(this), oldPassword, newPassword, repeatPassword)
                }
            } else {
                toast("Password Baru tidak cocok")
            }
        }
    }

    private fun handleUIState(it : UsersState){
        when(it){
            is UsersState.IsLoading -> isLoading(it.state)
            is UsersState.Error -> {
                toast("Cek kembali password lama anda dan kecocokan password baru")
                isLoading(false)
            }
            is UsersState.ShowToast -> toast(it.message)
            is UsersState.ChangePasswordValidation -> {
                it.oldPassword?.let {
                    setOldError(it)
                }
                it.newPassword?.let{
                    setNewError(it)
                }
                it.confirmPassword?.let{
                    setRepeatError(it)
                }
            }
            is UsersState.Reset -> {
                setOldError(null)
                setNewError(null)
                setRepeatError(null)
            }
            is UsersState.IsSuccess -> {
                when(it.what){
                    0 -> {
                        toast("Berhasil dibuat")
                        finish()
                    }
                    1 -> {
                        toast("Berhasil diupdate")
                        finish()
                    }
                    2 -> {
                        toast("Berhasil delete")
                        finish()
                    }
                }
            }
        }
    }
    private fun isLoading(state : Boolean){
        if (state) {
            passwordOld.isEnabled = false
            passwordNew.isEnabled = false
            passwordRepeat.isEnabled = false
            save_edt_change_password.isEnabled = false

            save_edt_change_password.visibility = View.GONE
            sh_save_password.visibility = View.VISIBLE
            //loading.isIndeterminate = true
            sh_save_password.startShimmerAnimation()
        } else {
            sh_save_password.apply {
                //isIndeterminate = false
                save_edt_change_password.visibility = View.VISIBLE
                sh_save_password.visibility = View.GONE
                sh_save_password.stopShimmerAnimation()
                //progress = 0
            }
            save_edt_change_password.isEnabled = true
            passwordOld.isEnabled = true
            passwordNew.isEnabled = true
            passwordRepeat.isEnabled = true
        }
    }
    private fun toast(message : String?) = Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    private fun setOldError(err : String?) { textInputPasswordOld.error = err }
    private fun setNewError(err : String?) { textInputPasswordNew.error = err }
    private fun setRepeatError(err : String?) { textInputPasswordRepeat.error = err }
}