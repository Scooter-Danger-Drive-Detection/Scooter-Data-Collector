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
    override fun getCurrentUserId(): Long {
        val userId = getUserId()
        if(userId == -1L){
            with(context.getSharedPreferences(AUTH_PREFERENCES, Context.MODE_PRIVATE).edit()){
                putLong(USER_ID, Date().time)
                apply()
            }
        }
        return if(userId != -1L) userId else getUserId()
    }

    private fun getUserId() =
        context.getSharedPreferences(AUTH_PREFERENCES, Context.MODE_PRIVATE).getLong(USER_ID, -1)

}