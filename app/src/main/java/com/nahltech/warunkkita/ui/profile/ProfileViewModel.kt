package com.nahltech.warunkkita.ui.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nahltech.warunkkita.data.models.User
import com.nahltech.warunkkita.data.network.ApiClient
import com.nahltech.warunkkita.utils.Constants
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
                    state.value = UsersState.Failed("Gagal mendapatkan response dari server")
                }
                state.value = UsersState.IsLoading(false)
            }
        })
    }
    fun editAccount(
        token: String,
        id: String,
        name: String,
        email: String,
        phone: String
    ) {
        state.value = UsersState.IsLoading(true)
        api.editAccount(token, id, name, email, phone)
            .enqueue(object : Callback<WrappedResponse<User>> {
                override fun onFailure(call: Call<WrappedResponse<User>>, t: Throwable) {
                    state.value = UsersState.Error(t.message)
                }

                override fun onResponse(
                    call: Call<WrappedResponse<User>>,
                    response: Response<WrappedResponse<User>>
                ) {
                    if (response.isSuccessful) {
                        val body = response.body() as WrappedResponse<User>
                        if (body.status.equals("1")) {
                            state.value = UsersState.IsSuccess(1)
                        } else {
                            state.value = UsersState.Failed("Gagal saat mengupdate akun. :(")

                        }
                    } else {
                        state.value = UsersState.Error("Kesalahan saat mengupdate akun")
                    }
                    state.value = UsersState.IsLoading(false)
                }
            })
    }

    fun editAddress(
        token: String,
        id: String,
        provinceId: String,
        cityId: String,
        districtId: String,
        villageId: String,
        completeAddress: String,
        postalCode: String
    ) {
        state.value = UsersState.IsLoading(true)
        api.editAddress(token, id, provinceId, cityId, districtId, villageId, completeAddress,postalCode)
            .enqueue(object : Callback<WrappedResponse<User>> {
                override fun onFailure(call: Call<WrappedResponse<User>>, t: Throwable) {
                    state.value = UsersState.Error(t.message)
                }

                override fun onResponse(
                    call: Call<WrappedResponse<User>>,
                    response: Response<WrappedResponse<User>>
                ) {
                    if (response.isSuccessful) {
                        val body = response.body() as WrappedResponse<User>
                        if (body.status.equals("1")) {
                            state.value = UsersState.IsSuccess(1)
                        } else {
                            state.value = UsersState.Error("Gagal saat mengupdate akun. :(")

                        }
                    } else {
                        state.value = UsersState.Error("Kesalahan saat mengupdate akun")
                    }
                    state.value = UsersState.IsLoading(false)
                }
            })
    }

    fun changePassword(token: String, id: String, oldPassword: String, newPassword: String, confirmPassword: String){
        state.value = UsersState.IsLoading(true)
        api.changePassword(token, id, oldPassword, newPassword, confirmPassword).enqueue(object : Callback<WrappedResponse<User>>{
            override fun onFailure(call: Call<WrappedResponse<User>>, t: Throwable) {
                state.value = UsersState.Error(t.message)
            }

            override fun onResponse(call: Call<WrappedResponse<User>>, response: Response<WrappedResponse<User>>) {
                if(response.isSuccessful){
                    val body = response.body() as WrappedResponse<User>
                    if(body.status.equals("1")){
                        state.value = UsersState.IsSuccess(1)
                    }else{
                        state.value = UsersState.Failed("Gagal saat mengupdate recipe. :(")

                    }
                }else{
                    state.value = UsersState.Error("Kesalahan saat mengupdate recipe")
                }
                state.value = UsersState.IsLoading(false)
            }
        })
    }

    fun validateEditAccount(
        name: String,
        email: String,
        phone: String
    ): Boolean {
        state.value = UsersState.Reset
        if (name.isEmpty() || email.isEmpty() || phone.isEmpty() ) {
            state.value = UsersState.ShowToast("Mohon isi semua form")
            return false
        }
        if (!Constants.isValidEmailPhone(email)) {
            state.value = UsersState.ValidateEditAccount(email = "Email tidak valid")
            return false
        }
        return true
    }

    fun validateAddress(
        province: String,
        city: String,
        district: String,
        village: String,
        completeAddress: String,
        postalCode: String
    ): Boolean {
        state.value = UsersState.Reset
        if (province.isEmpty() || city.isEmpty() || district.isEmpty() || village.isEmpty() || completeAddress.isEmpty() || postalCode.isEmpty()) {
            state.value = UsersState.ShowToast("Mohon isi semua form")
            return false
        }
        return true
    }

    fun validate(oldPassword : String, newPassword: String, confirmPassword: String) : Boolean{
        state.value = UsersState.Reset
        if(oldPassword.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()){
            state.value = UsersState.ShowToast("Mohon isi semua form")
            return false
        }

        if(!Constants.isValidPassword(oldPassword)){
            state.value = UsersState.ChangePasswordValidation(oldPassword = "Password setidaknya 6 Karakter")
            return false
        }

        if(!Constants.isValidPassword(newPassword)){
            state.value = UsersState.ChangePasswordValidation(newPassword = "Password setidaknya 6 Karakter")
            return false
        }

        if(!Constants.isValidPassword(confirmPassword)){
            state.value = UsersState.ChangePasswordValidation(confirmPassword = "Password setidaknya 6 Karakter")
            return false
        }
        return true
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
    data class Failed(var message: String) : UsersState()
    data class IsSuccess(var what : Int? = null) : UsersState()
    object Reset : UsersState()
}