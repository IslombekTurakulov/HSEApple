package com.iuturakulov.hseapple.view.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.iuturakulov.hseapple.R
import com.iuturakulov.hseapple.utils.CourseSelection
import com.iuturakulov.hseapple.utils.SELECTION
import com.iuturakulov.hseapple.utils.USER_CHAT
import com.iuturakulov.hseapple.utils.replaceFragment
import com.iuturakulov.hseapple.view.activities.RequestsActivity
import kotlinx.android.synthetic.main.component_grade.*
import kotlinx.android.synthetic.main.fragment_profile.*


class ProfileFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fullNameOfProfile.text = USER_CHAT.name
        "${if (USER_CHAT.role == "default") "Student" else if (USER_CHAT.role == "teacher") "Teacher" else "Assistant"}, ${if (SELECTION == CourseSelection.CHOSEN_SECOND) "2 course" else "3 course"}".also { courseInfo.text = it }
        profileRequestBtn.setOnClickListener {
            // (RequestsActivity(), false)
        }
        profileUsersBtn.setOnClickListener {
            val intent = Intent()
        }
    }
}