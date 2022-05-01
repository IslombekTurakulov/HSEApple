package com.iuturakulov.hseapple.view.activities

import android.app.ProgressDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toBitmap
import com.cometchat.pro.constants.CometChatConstants
import com.cometchat.pro.core.CometChat
import com.cometchat.pro.exceptions.CometChatException
import com.cometchat.pro.models.Conversation
import com.cometchat.pro.models.Group
import com.cometchat.pro.models.User
import com.cometchat.pro.uikit.ui_components.chats.CometChatConversationList
import com.cometchat.pro.uikit.ui_components.groups.group_list.CometChatGroupList
import com.cometchat.pro.uikit.ui_components.messages.message_list.CometChatMessageListActivity
import com.cometchat.pro.uikit.ui_resources.constants.UIKitConstants
import com.cometchat.pro.uikit.ui_resources.utils.ErrorMessagesUtils
import com.cometchat.pro.uikit.ui_resources.utils.custom_alertDialog.CustomAlertDialogHelper
import com.cometchat.pro.uikit.ui_resources.utils.item_clickListener.OnItemClickListener
import com.iuturakulov.hseapple.R
import com.iuturakulov.hseapple.model.models.Courses
import com.iuturakulov.hseapple.view.adapters.CoursesAdapter
import kotlinx.android.synthetic.main.activity_available_courses.*

class AvailableCoursesActivity : AppCompatActivity(R.layout.activity_available_courses) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_available_courses)
        title = "Доступные курсы"
        val list: ArrayList<Courses> = arrayListOf()
        list.add(Courses(resources.getString(R.string.second_course), ""))
        list.add(Courses(resources.getString(R.string.third_course), ""))
        updateItems(list)
    }

    private fun updateItems(list: ArrayList<Courses>) {
        val coursesAdapter = CoursesAdapter(list)
        coursesRecyclerView.adapter = coursesAdapter
        swipe_refresh_layout.setOnRefreshListener {
            coursesAdapter.updateData(list)
            swipe_refresh_layout.isRefreshing = false
        }
    }

}