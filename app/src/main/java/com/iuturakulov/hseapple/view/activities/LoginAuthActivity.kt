package com.iuturakulov.hseapple.view.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.cometchat.pro.core.CometChat
import com.cometchat.pro.exceptions.CometChatException
import com.cometchat.pro.models.User
import com.google.gson.Gson
import com.hse.auth.utils.AuthConstants
import com.hse.core.BaseApplication
import com.hse.core.ui.BaseActivity
import com.iuturakulov.hseapple.R
import com.iuturakulov.hseapple.model.api.UserEntity
import com.iuturakulov.hseapple.utils.*
import kotlinx.android.synthetic.main.activity_login.*
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import timber.log.Timber
import java.io.IOException
import java.sql.Timestamp

class LoginAuthActivity : BaseActivity() {

    private lateinit var preferenceManager: PreferenceManager

    override fun onCreate(savedInstanceState: Bundle?) {
        BaseApplication.appComponent.inject(this)

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)
        preferenceManager = PreferenceManager(this)
        authLogButton.setOnClickListener {
            authLogButton!!.isClickable = false
             UserEntity(
                 id = 1,
                 firstname =
                 "Туракулов",
                 lastname = "Исломбек",
                 fullName = "Туракулов Исломбек Улугбекович",
                 email = "gsosnovskij@hse.ru",
                 createdAt = null
             ).also { USER = it }
            createUser()
            // AuthHelper.login(this, 1)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_LOGIN) {
            if (resultCode != RESULT_OK || data == null) {
                return
            }
            ACCESS_TOKEN = data.getStringExtra(AuthConstants.KEY_ACCESS_TOKEN)!!
            REFRESH_TOKEN = data.getStringExtra(AuthConstants.KEY_REFRESH_TOKEN)!!
            Timber.d("Token got, success")
            Toast.makeText(this, "Success", Toast.LENGTH_LONG).show()
            initializeToken()
        }
    }

    private fun initializeToken() {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url("ip/auth")
            .get()
            .addHeader("Content-Type", "application/json")
            .addHeader("Authorization", ACCESS_TOKEN)
            .build()
        var response: Response? = null
        try {
            response = client.newCall(request).execute()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        if (response != null) {
            val res: UserEntity =
                Gson().fromJson(response.body()?.string() ?: "", UserEntity::class.java)
            UserEntity(
                id = res.id,
                firstname = "",
                lastname = "",
                fullName = res.fullName,
                email = res.email,
                createdAt = Timestamp.valueOf(res.createdAt.toString())
            ).also { USER = it }
            createUser()
        }
    }


    private fun createUser() {
        val email = USER.email!!.split("@")[0]
        CometChat.getUser(email, object : CometChat.CallbackListener<User>() {
            override fun onSuccess(user: User) {
                login(user)
            }

            override fun onError(e: CometChatException) {
                CometChat.createUser(
                    User(email, USER.fullName),
                    AUTH_KEY,
                    object : CometChat.CallbackListener<User>() {
                        override fun onSuccess(user: User) {
                            login(user)
                        }

                        override fun onError(e: CometChatException) {
                            authLogButton!!.isClickable = true
                        }
                    })
            }
        })
    }

    private fun login(user: User) {
        CometChat.login(user.uid, AUTH_KEY, object : CometChat.CallbackListener<User?>() {
            override fun onSuccess(user: User?) {
                if (user != null) {
                    USER_CHAT = user
                }
            }

            override fun onError(e: CometChatException) {
                Toast.makeText(applicationContext, e.details.toString(), Toast.LENGTH_SHORT)
                    .show()
            }
        })
        val startupIntent = Intent(this, AvailableCoursesActivity::class.java)
        startActivity(startupIntent)
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        finish()
    }
}