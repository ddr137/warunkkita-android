package com.nahltech.warunkkita.ui.profile

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.nahltech.warunkkita.R
import com.nahltech.warunkkita.utils.Constants
import kotlinx.android.synthetic.main.activity_bank_account.*

class BankAccountActivity : AppCompatActivity() {
    private lateinit var profileViewModel: ProfileViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bank_account)
        toolbarUI()
        setupViewModel()
    }

    private fun toolbarUI() {
        setSupportActionBar(toolbar_edit_bank_account)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        toolbar_edit_bank_account.setNavigationIcon(R.drawable.ic_back_white)
        toolbar_edit_bank_account.setNavigationOnClickListener { finish() }
    }

    private fun setupViewModel() {
        profileViewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        doUpdate()
        profileViewModel.getState().observe(this, Observer {
            handleUIState(it)
        })
    }

    private fun doUpdate() {
        save_edt_bank_account.setOnClickListener {
            val bankName  = edt_bank_name.text.toString().trim()
            val noBankAccount  = edt_no_account_bank.text.toString().trim()
            val ownerBankAccount  = edt_name_owner_bank_account.text.toString().trim()
            val branch  = edt_branch_account_bank.text.toString().trim()
            if(profileViewModel.validateBankAccount(bankName, noBankAccount, ownerBankAccount, branch)){
                profileViewModel.changeBankAccount(Constants.getToken(this),
                    Constants.getIdUser(this), bankName, noBankAccount, ownerBankAccount, branch)
            }
        }
    }

    private fun handleUIState(it : UsersState){
        when(it){
            is UsersState.IsLoading -> isLoading(it.state)
            is UsersState.Error -> {
                isLoading(false)
            }
            is UsersState.ShowToast -> toast(it.message)
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
            edt_bank_name.isEnabled = false
            edt_no_account_bank.isEnabled = false
            edt_name_owner_bank_account.isEnabled = false
            edt_branch_account_bank.isEnabled = false
            save_edt_bank_account.isEnabled = false

            save_edt_bank_account.visibility = View.GONE
            sh_save_bank_account.visibility = View.VISIBLE
            //loading.isIndeterminate = true
            sh_save_bank_account.startShimmerAnimation()
        } else {
            sh_save_bank_account.apply {
                //isIndeterminate = false
                save_edt_bank_account.visibility = View.VISIBLE
                sh_save_bank_account.visibility = View.GONE
                sh_save_bank_account.stopShimmerAnimation()
                //progress = 0
            }
            save_edt_bank_account.isEnabled = true
            edt_bank_name.isEnabled = true
            edt_no_account_bank.isEnabled = true
            edt_name_owner_bank_account.isEnabled = true
            edt_branch_account_bank.isEnabled = true
        }
    }
    private fun toast(message : String?) = Toast.makeText(this, message, Toast.LENGTH_LONG).show()

}