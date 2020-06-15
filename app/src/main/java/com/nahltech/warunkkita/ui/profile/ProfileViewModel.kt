package com.nahltech.warunkkita.ui.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nahltech.warunkkita.data.models.User
import com.nahltech.warunkkita.data.network.ApiClient
import com.nahltech.warunkkita.utils.SingleLiveEvent
import com.nahltech.warunkkita.utils.WrappedResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileViewModel: ViewModel() {
    private var users = MutableLiveData<User>()
    private var state : SingleLiveEvent<UsersState> = SingleLiveEvent()
    private var api = ApiClient.instance()

    fun fetchProfile(token: String){
        state.value = UsersState.IsLoading(true)
        api.getProfile(token).enqueue(object : Callback<WrappedResponse<User>> {
            override fun onFailure(call: Call<WrappedResponse<User>>, t: Throwable) {
                println(t.message)
                state.value = UsersState.Error(t.message)
            }

            override fun onResponse(call: Call<WrappedResponse<User>>, response: Response<WrappedResponse<User>>) {
                if(response.isSuccessful){
                    val body = response.body() as WrappedResponse<User>
                    if(body.status.equals("1")){
                        val r = body.data
                        users.postValue(r)
                    }
                }else{
                    state.value = UsersState.Error("Gagal mendapatkan response dari server")
                }
                state.value = UsersState.IsLoading(false)
            }
        })
    }

    fun getUsers() = users
    fun getState()  = state

}

sealed class UsersState {
    data class ShowToast(var message : String) : UsersState()
    data class IsLoading(var state : Boolean = false) : UsersState()
    data class ChangePasswordValidation(var oldPassword : String? = null, var newPassword : String? = null, var confirmPassword : String? = null) : UsersState()
    data class ValidateEditAccount(
        var name: String? = null,
        var email: String? = null,
        var phone: String? = null,
        var gender : String? = null
    ) : UsersState()
    data class Error(var err : String?) : UsersState()
    data class IsSuccess(var what : Int? = null) : UsersState()
    object Reset : UsersState()
}