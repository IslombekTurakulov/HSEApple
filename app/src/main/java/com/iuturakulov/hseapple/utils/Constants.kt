package com.iuturakulov.hseapple.utils

import android.content.SharedPreferences
import com.cometchat.pro.models.User
import com.hse.auth.AuthHelper
import com.iuturakulov.hseapple.model.api.PostEntity
import com.iuturakulov.hseapple.model.api.RequestEntity
import com.iuturakulov.hseapple.model.api.UserEntity

lateinit var AUTH: AuthHelper

lateinit var preferences: SharedPreferences

lateinit var CURRENT_UID: String
lateinit var ACCESS_TOKEN: String
lateinit var REFRESH_TOKEN: String
const val TEMP_TOKEN : String = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOiJtaWNyb3NvZnQ6aWRlbnRpdHlzZXJ2ZXI6MGQ0NmFlMDUtM2JiYy00MzQ2LWEzYTMtMWQ3MzJiNDllYTUzIiwiaXNzIjoiaHR0cDovL2F1dGguaHNlLnJ1L2FkZnMvc2VydmljZXMvdHJ1c3QiLCJpYXQiOjE2NTExMzgyNzIsIm5iZiI6MTY1MTEzODI3MiwiZXhwIjoxNjUxMTQxODcyLCJnaXZlbl9uYW1lIjoi0JPRgNC40LPQvtGA0LjQuSIsImNvbW1vbm5hbWUiOiLQodC-0YHQvdC-0LLRgdC60LjQuSDQk9GA0LjQs9C-0YDQuNC5INCc0LjRhdCw0LnQu9C-0LLQuNGHIiwiZmFtaWx5X25hbWUiOiLQodC-0YHQvdC-0LLRgdC60LjQuSIsImVtYWlsIjoiZ21zb3Nub3Zza2l5QGVkdS5oc2UucnUiLCJhcHB0eXBlIjoiUHVibGljIiwiYXBwaWQiOiIwZDQ2YWUwNS0zYmJjLTQzNDYtYTNhMy0xZDczMmI0OWVhNTMiLCJhdXRobWV0aG9kIjoidXJuOm9hc2lzOm5hbWVzOnRjOlNBTUw6Mi4wOmFjOmNsYXNzZXM6UGFzc3dvcmRQcm90ZWN0ZWRUcmFuc3BvcnQiLCJhdXRoX3RpbWUiOiIyMDIyLTA0LTI3VDE1OjU3OjI2LjU5M1oiLCJ2ZXIiOiIxLjAifQ.mG2KAlMopVguX952qDxahpNsJvCylxCCEldK-_filqo"
lateinit var USER: UserEntity

lateinit var USER_CHAT: User
lateinit var CURRENT_COURSE: CurrentCourse

lateinit var arrayOfRequestCourses: ArrayList<RequestEntity>

enum class CourseSelection {
    CHOSEN_SECOND,
    CHOSEN_THIRD
}

var SELECTION: CourseSelection = CourseSelection.CHOSEN_SECOND

lateinit var postInfo: PostEntity

const val REQUEST_LOGIN = 1

const val APP_ID = "2080788f45f25844"
const val API_KEY = "6b382a280ea985c4d1bf1735357b9756909d63c9"
const val AUTH_KEY = "fdfb9b0cee2a3643ed78aa6d2933644be6762d9a"
const val API_COUNTRY = "EU"

const val TS_DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS"

const val TOKEN_API = "123"

const val KEY_COLLECTION_USERS = "users"
const val KEY_NAME = "full_name"
const val KEY_EMAIL = "email"
const val KEY_PASSWORD = "password"
const val KEY_PREFERENCE_NAME = "chatAppPreference"
const val KEY_IS_SIGNED_IN = "isSignedIn"
const val KEY_USER_ID = "userId"
const val KEY_IMAGE = "image"