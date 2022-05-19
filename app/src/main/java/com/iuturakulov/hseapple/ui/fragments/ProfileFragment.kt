package com.iuturakulov.hseapple.ui.fragments

import android.R.id.message
import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.cometchat.pro.core.CometChat
import com.iuturakulov.hseapple.R
import com.iuturakulov.hseapple.ui.activities.*
import com.iuturakulov.hseapple.utils.*
import kotlinx.android.synthetic.main.fragment_profile.*
import timber.log.Timber
import java.util.*


class ProfileFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (CometChat.getLoggedInUser().avatar != null) {
            profileAvatar.setAvatar(CometChat.getLoggedInUser().avatar)
        }
        fullNameOfProfile.text = USER_CHAT!!.name
        "${
            when (USER_CHAT!!.role) {
                "default" -> requireActivity().getString(R.string.status_student)
                "teacher" -> requireActivity().getString(R.string.status_teacher)
                else -> requireActivity().getString(R.string.status_assistant)
            }
        }, ${
            when (SELECTION) {
                CourseSelection.CHOSEN_SECOND -> requireActivity().getString(R.string.status_second_course)
                else -> requireActivity().getString(R.string.status_third_course)
            }
        }".also { courseInfo.text = it }
        when (USER_CHAT!!.role) {
            "default" -> {
                teacherFeatures.visibility = View.GONE
            }
            "teacher" -> {
                teacherFeatures.visibility = View.VISIBLE
                initializeTeacherFeatures()
            }
            "assistant" -> {
                teacherFeatures.visibility = View.GONE
            }
        }
        initializeDefaultFeatures()
    }

    private fun initializeDefaultFeatures() {
        profileHelpBtn.setOnClickListener {
            val intent = Intent(
                Intent.ACTION_SENDTO, Uri.fromParts(
                    "mailto", "letter.hseapple@gmail.com", null
                )
            )
            intent.putExtra(Intent.EXTRA_TEXT, message)
            startActivity(Intent.createChooser(intent, "Choose an Email client :"))
        }
        profileCoursesBtn.setOnClickListener {
            val startupIntent = Intent(requireContext(), AvailableCoursesActivity::class.java)
            startupIntent.flags =
                Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(startupIntent)
            requireActivity().overridePendingTransition(
                android.R.anim.fade_in,
                android.R.anim.fade_out
            )
        }
        profileLogOutBtn.setOnClickListener {
            CometChat.removeLoginListener(USER_CHAT!!.uid)
            val startupIntent =
                Intent(APP_ACTIVITY.applicationContext, LoginAuthActivity::class.java)
            startupIntent.flags =
                Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(startupIntent)
            requireActivity().overridePendingTransition(
                android.R.anim.fade_in,
                android.R.anim.fade_out
            )
            APP_ACTIVITY.finish()
            Timber.d("Logout completed successfully")
        }

        profileLanguageBtn.setOnClickListener {
            LANGUAGE = if (LANGUAGE == "en") "ru" else "en"
            val locale = Locale(LANGUAGE)
            Locale.setDefault(locale)
            val config = Configuration()
            config.locale = locale
            APP_ACTIVITY.resources.updateConfiguration(
                config,
                APP_ACTIVITY.resources.displayMetrics
            )
            restartActivity()
            // Toast.makeText(requireContext(), requireActivity().getString(R.string.selected_language), Toast.LENGTH_SHORT).show()
        }
    }

    private fun initializeTeacherFeatures() {
        profileRequestBtn.setOnClickListener {
            val startupIntent = Intent(requireContext(), RequestsActivity::class.java)
            startActivity(startupIntent)
            requireActivity().overridePendingTransition(
                android.R.anim.fade_in,
                android.R.anim.fade_out
            )
        }
        profileAssistantsBtn.setOnClickListener {
            val startupIntent = Intent(requireContext(), ListOfAssistantsActivity::class.java)
            startActivity(startupIntent)
            requireActivity().overridePendingTransition(
                android.R.anim.fade_in,
                android.R.anim.fade_out
            )
        }
        profileUsersBtn.setOnClickListener {
            val startupIntent = Intent(requireContext(), ListOfUsersActivity::class.java)
            startActivity(startupIntent)
            requireActivity().overridePendingTransition(
                android.R.anim.fade_in,
                android.R.anim.fade_out
            )
        }
    }
}