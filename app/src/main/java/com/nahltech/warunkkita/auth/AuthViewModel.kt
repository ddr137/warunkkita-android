package com.nahltech.warunkkita.auth

import androidx.lifecycle.ViewModel
import com.nahltech.warunkkita.models.User
import com.nahltech.warunkkita.network.ApiClient
import com.nahltech.warunkkita.utils.Constants
import com.nahltech.warunkkita.utils.SingleLiveEvent
import com.nahltech.warunkkita.utils.WrappedResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthViewModel : ViewModel(){
    private var state: SingleLiveEvent<UserState> = SingleLiveEvent()
    private var api = ApiClient.instance()

    fun login(emailPhone: String, password: String) {
        state.value = UserState.IsLoading(true)
        api.login(emailPhone, password).enqueue(object : Callback<WrappedResponse<User>> {
            override fun onFailure(call: Call<WrappedResponse<User>>, t: Throwable) {
                println(t.message)
                state.value = UserState.Error(t.message)
            }
            override fun onResponse(call: Call<WrappedResponse<User>>, response: Response<WrappedResponse<User>>) {
                if (response.isSuccessful) {
                    val body = response.body() as WrappedResponse<User>
                    if (body.status.equals("1")) {
                        state.value = UserState.Success(
                            "Bearer ${body.data!!.api_token}",
                            "${body.data!!.id}",
                            "${body.data!!.name}"
                        )
                    } else {
                        state.value = UserState.Failed("Login gagal")
                    }
                } else {
                    state.value = UserState.Error("Email atau Password salah")
                }
                state.value = UserState.IsLoading(false)
            }
        })
    }
    fun validateLogin(emailPhone: String, password: String): Boolean {
        state.value = UserState.Reset
        if (emailPhone.isEmpty() || password.isEmpty()) {
            state.value =
                UserState.ShowToast("Mohon isi semua form")
            return false
        }
        if (!Constants.isValidPassword(password)) {
            state.value =
                UserState.Validate(password = "Password setidaknya 6 karakter")
            return false
        }
        return true
    }

    fun getState() = state
}

sealed class UserState {
    data class Error(var err: String?) : UserState()
    data class ShowToast(var message: String) : UserState()
    data class Validate(
        var emailPhone: String? = null,
        var password: String? = null
    ) : UserState()

    data class ValidateRegister(
        var name: String? = null,
        var email: String? = null,
        var phone: String? = null,
        var password: String? = null,
        var gender : String? = null,
        var province : String? = null,
        var city : String? = null,
        var district : String? = null,
        var village : String? = null,
        var completeAddress : String? = null
    ) : UserState()

    data class IsLoading(var state: Boolean) : UserState()
    data class Success(var token: String, var id: String, var name: String) : UserState()
    data class IsSuccessRegister(var what: Int? = null) : UserState()
    data class Failed(var message: String) : UserState()
    object Reset : UserState()
}