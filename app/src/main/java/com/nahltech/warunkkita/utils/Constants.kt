package com.nahltech.warunkkita.utils

import android.content.Context

class Constants {
    companion object {
        const val API_ENDPOINT = "https://warunkkita.com/"

        /** Token **/

        fun getToken(context : Context) : String{
            val pref = context.getSharedPreferences("USER", Context.MODE_PRIVATE)
            val token = pref.getString("TOKEN", "undefined")
            return token!!
        }

        fun setToken(context: Context, token : String){
            val pref = context.getSharedPreferences("USER", Context.MODE_PRIVATE)
            pref.edit().apply {
                putString("TOKEN", token)
                apply()
            }
        }

        fun clearToken(context: Context){
            val pref = context.getSharedPreferences("USER", Context.MODE_PRIVATE)
            pref.edit().clear().apply()
        }


        /** ID User **/

        fun getIdUser(context : Context) : String{
            val pref = context.getSharedPreferences("USER", Context.MODE_PRIVATE)
            val id = pref.getString("ID", "undefined")
            return id!!
        }

        fun setIdUser(context: Context, id : String){
            val pref = context.getSharedPreferences("USER", Context.MODE_PRIVATE)
            pref.edit().apply {
                putString("ID", id)
                apply()
            }
        }

        fun isValidEmailPhone(emailPhone : String) = android.util.Patterns.EMAIL_ADDRESS.matcher(emailPhone).matches()
        fun isValidPassword(pass : String) = pass.length > 5
    }
}