package com.iuturakulov.hseapple.utils

import android.content.SharedPreferences
import com.cometchat.pro.models.User
import com.hse.auth.AuthHelper
import com.hse.auth.ui.models.UserAccountData
import com.hse.auth.ui.models.UserEntity

lateinit var AUTH: AuthHelper

lateinit var preferences: SharedPreferences

lateinit var CURRENT_UID: String
lateinit var ACCESS_TOKEN: String
lateinit var REFRESH_TOKEN: String
lateinit var USER: UserAccountData
lateinit var USER_CHAT: User
lateinit var USER_ENTITY: UserEntity
lateinit var CURRENT_COURSE: CurrentCourse

enum class CourseSelection {
    CHOSEN_SECOND,
    CHOSEN_THIRD
}

lateinit var SELECTION: CourseSelection

const val VIEW_TYPE_SENT = 1
const val VIEW_TYPE_RECEIVED = 2
const val REQUEST_LOGIN = 1

const val APP_ID = "2080788f45f25844"
const val API_KEY = "6b382a280ea985c4d1bf1735357b9756909d63c9"
const val AUTH_KEY = "fdfb9b0cee2a3643ed78aa6d2933644be6762d9a"
const val API_COUNTRY = "EU"

const val KEY_COLLECTION_USERS = "users"
const val KEY_NAME = "full_name"
const val KEY_EMAIL = "email"
const val KEY_PASSWORD = "password"
const val KEY_PREFERENCE_NAME = "chatAppPreference"
const val KEY_IS_SIGNED_IN = "isSignedIn"
const val KEY_USER_ID = "userId"
const val KEY_IMAGE = "image"