package com.scooter.datacollector.auth

import android.content.Context
import com.scooter.datacollector.domain.auth.IAuth
import java.util.Date
import kotlin.random.Random

class Auth(val context: Context) : IAuth{
    companion object{
        private const val AUTH_PREFERENCES = "com.scooter.datacollector.AUTH"
        private const val USER_ID = "USER_ID"
    }
    override fun getCurrentUserId(): Int {
        val userId = getUserId()
        if(userId == -1){
            with(context.getSharedPreferences(AUTH_PREFERENCES, Context.MODE_PRIVATE).edit()){
                putInt(USER_ID, Random(Date().time).nextInt())
                apply()
            }
        }
        return if(userId != -1) userId else getUserId()
    }

    private fun getUserId() =
        context.getSharedPreferences(AUTH_PREFERENCES, Context.MODE_PRIVATE).getInt(USER_ID, -1)

}