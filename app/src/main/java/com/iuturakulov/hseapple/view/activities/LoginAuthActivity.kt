package com.iuturakulov.hseapple.view.activities

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.StrictMode
import android.widget.Toast
import com.cometchat.pro.core.CometChat
import com.cometchat.pro.exceptions.CometChatException
import com.cometchat.pro.models.User
import com.google.gson.Gson
import com.hse.auth.AuthHelper
import com.hse.auth.utils.AuthConstants
import com.hse.core.BaseApplication
import com.hse.core.ui.BaseActivity
import com.iuturakulov.hseapple.R
import com.iuturakulov.hseapple.model.UserEntity
import com.iuturakulov.hseapple.utils.*
import kotlinx.android.synthetic.main.activity_login_auth.*
import okhttp3.OkHttpClient
import okhttp3.Request
import timber.log.Timber
import java.io.IOException


class LoginAuthActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        BaseApplication.appComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_auth)
        authLogButton.setOnClickListener {
            authLogButton!!.isClickable = false
            /* UserEntity(
                 id = 1,
                 fullName = "Туракулов Исломбек Улугбекович",
                 email = "iuturakulov@edu.hse.ru"
             ).also { USER = it }
             createUser()*/
            AuthHelper.login(this, 1)
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_LOGIN) {
            if (resultCode != RESULT_OK || data == null) {
                return
            }
            ACCESS_TOKEN = data.getStringExtra(AuthConstants.KEY_ACCESS_TOKEN).toString()
            REFRESH_TOKEN = data.getStringExtra(AuthConstants.KEY_REFRESH_TOKEN).toString()
            Timber.d("Token got, success")
            Toast.makeText(this, "Success", Toast.LENGTH_LONG).show()
            authLoginCredentials()
        }
        authLogButton!!.isClickable = true
    }

    private fun authLoginCredentials() {
        val sdkInt = Build.VERSION.SDK_INT;
        if (sdkInt > 8) {
            val policy: StrictMode.ThreadPolicy = StrictMode.ThreadPolicy.Builder()
                .permitAll().build()
            StrictMode.setThreadPolicy(policy)
            val client = OkHttpClient().newBuilder()
                .build()
            val request = Request.Builder()
                .url("${IP_ADDRESS}/auth")
                .method("GET", null)
                .addHeader(
                    "Authorization",
                    ACCESS_TOKEN
                )
                .addHeader("Content-Type", "application/json")
                .build()
            try {
                val response = client.newCall(request).execute()
                Timber.d("Login Auth: ${response.isSuccessful}")
                if (response.isSuccessful) {
                    USER = Gson().fromJson(response.body()?.string() ?: "", UserEntity::class.java)
                    Timber.w(USER.toString())
                } else {
                    toast("Oops... Auth failure")
                }
            } catch (exception: IOException) {
                exception.printStackTrace()
            }
            createUser()
        }
    }

    private fun createUser() {
        val email = USER.email!!.split("@")[0]
        CometChat.login(email, AUTH_KEY, object : CometChat.CallbackListener<User>() {
            override fun onSuccess(p0: User?) {
                USER_CHAT = CometChat.getLoggedInUser()
                Timber.d("Success: ${USER_CHAT.role} ${USER_CHAT.name}")
            }

            override fun onError(p0: CometChatException?) {
                Timber.d("Registering user")
                val user = User()
                user.uid = email
                user.name = USER.fullName
                CometChat.createUser(
                    user,
                    AUTH_KEY,
                    object : CometChat.CallbackListener<User>() {
                        override fun onSuccess(user: User) {
                            CometChat.login(
                                user.uid,
                                AUTH_KEY,
                                object : CometChat.CallbackListener<User>() {
                                    override fun onSuccess(p0: User?) {
                                        USER_CHAT = CometChat.getLoggedInUser()
                                        Timber.d("Success: ${USER_CHAT.role} ${USER_CHAT.name}")
                                    }

                                    override fun onError(p0: CometChatException?) {
                                        Timber.d("Error in login")
                                    }
                                })
                        }

                        override fun onError(e: CometChatException) {
                            authLogButton!!.isClickable = true
                        }
                    })
            }
        })
        CometChat.updateUser(
            CometChat.getLoggedInUser(),
            API_KEY,
            object : CometChat.CallbackListener<User>() {
                override fun onSuccess(user: User) {
                    Timber.d("updateUser: $user")
                }

                override fun onError(e: CometChatException) {
                    Timber.e("updateUser: ${e.message}")
                }
            })
        USER_CHAT = CometChat.getLoggedInUser()
        USER_CHAT.role = "teacher"
        toast("Success: ${USER_CHAT.role} ${USER_CHAT.name}")
        val intent = Intent(this, AvailableCoursesActivity::class.java);
        intent.flags =
            Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        finish()
    }
}
