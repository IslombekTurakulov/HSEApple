package com.iuturakulov.hseapple.view.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.auth0.android.jwt.JWT
import com.cometchat.pro.core.CometChat
import com.cometchat.pro.exceptions.CometChatException
import com.cometchat.pro.models.User
import com.hse.auth.AuthHelper
import com.hse.auth.utils.AuthConstants
import com.hse.core.BaseApplication
import com.hse.core.ui.BaseActivity
import com.iuturakulov.hseapple.R
import com.iuturakulov.hseapple.model.api.UserEntity
import com.iuturakulov.hseapple.utils.*
import kotlinx.android.synthetic.main.activity_login.*
import timber.log.Timber
import java.sql.Timestamp
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class LoginAuthActivity : BaseActivity() {

    private lateinit var preferenceManager: PreferenceManager

    override fun onCreate(savedInstanceState: Bundle?) {
        BaseApplication.appComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        preferenceManager = PreferenceManager(this)
        authLogButton.setOnClickListener {
            authLogButton!!.isClickable = false
            /*  USER = UserAccountData(
                  "iuturakulov@edu.hse.ru",
                  "",
                  "Туракулов Исломбек Улугбекович",
                  "",
                  "",
                  2000L,
                  2000L,
                  "asfkcmxblksnfkas"
              )
              initializeChatAndLogin()
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
            ACCESS_TOKEN = data.getStringExtra(AuthConstants.KEY_ACCESS_TOKEN)!!
            REFRESH_TOKEN = data.getStringExtra(AuthConstants.KEY_REFRESH_TOKEN)!!
            AuthHelper.getClientId()
            Timber.d("Token got, success")
            Toast.makeText(this, "Success", Toast.LENGTH_LONG).show()
            val parsedJWT = JWT(ACCESS_TOKEN)
            val formatter = DateTimeFormatter.ofPattern(TS_DATE_PATTERN)
            val localDateTime: LocalDateTime =
                LocalDateTime.from(formatter.parse(parsedJWT.getClaim("auth_time").asString()))
            UserEntity(
                firstname = parsedJWT.getClaim(
                    "family_name"
                ).asString(),
                lastname = parsedJWT.getClaim(
                    "given_name"
                ).asString(),
                email = parsedJWT.getClaim("email").asString(),
                createdAt = Timestamp.valueOf(localDateTime.toString())
            ).also { USER = it }
            initializeChatAndLogin()
        }
    }

    private fun initializeChatAndLogin() {
        createUser()
        val startupIntent = Intent(this, AvailableCoursesActivity::class.java)
        startupIntent.flags =
            Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(startupIntent)
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        finish()
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
                            Toast.makeText(
                                applicationContext,
                                e.details,
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }
                    })
            }
        })
    }

    private fun login(user: User) {
        CometChat.login(user.uid, AUTH_KEY, object : CometChat.CallbackListener<User?>() {
            override fun onSuccess(user: User?) {
                USER_CHAT = user!!
                finish()
            }

            override fun onError(e: CometChatException) {
                Toast.makeText(applicationContext, e.details.toString(), Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }
}