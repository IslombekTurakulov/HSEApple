package com.iuturakulov.hseapple.ui.fragments

import android.app.ProgressDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.cometchat.pro.constants.CometChatConstants
import com.cometchat.pro.core.CometChat
import com.cometchat.pro.exceptions.CometChatException
import com.cometchat.pro.models.Conversation
import com.cometchat.pro.models.Group
import com.cometchat.pro.models.User
import com.cometchat.pro.uikit.ui_components.chats.CometChatConversationList
import com.cometchat.pro.uikit.ui_components.groups.group_list.CometChatGroupList
import com.cometchat.pro.uikit.ui_components.messages.message_list.CometChatMessageListActivity
import com.cometchat.pro.uikit.ui_components.users.user_list.CometChatUserList
import com.cometchat.pro.uikit.ui_resources.constants.UIKitConstants
import com.cometchat.pro.uikit.ui_resources.utils.ErrorMsgUtils
import com.cometchat.pro.uikit.ui_resources.utils.custom_alertDialog.CustomAlertDialogHelper
import com.cometchat.pro.uikit.ui_resources.utils.custom_alertDialog.OnAlertDialogButtonClickListener
import com.cometchat.pro.uikit.ui_resources.utils.item_clickListener.OnItemClickListener
import com.iuturakulov.hseapple.R
import com.iuturakulov.hseapple.utils.APP_ACTIVITY
import com.iuturakulov.hseapple.utils.LANGUAGE
import java.util.*

class ChatsFragment : Fragment(), OnAlertDialogButtonClickListener {

    private var progressDialog: ProgressDialog? = null
    private var groupPassword: String? = null
    private var group: Group? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_load_fragment, container, false)
    }

    override fun onResume() {
        super.onResume()
        val locale = Locale(LANGUAGE)
        Locale.setDefault(locale)
        val config = Configuration()
        config.locale = locale
        APP_ACTIVITY.resources.updateConfiguration(
            config,
            APP_ACTIVITY.resources.displayMetrics
        )
        loadFragment(CometChatConversationList())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadFragment(CometChatConversationList())
        initializeListeners()
    }

    private fun initializeListeners() {
        CometChatUserList.setItemClickListener(object : OnItemClickListener<Any>() {
            override fun OnItemClick(t: Any, position: Int) {
                Toast.makeText(requireContext(), (t as User).name, Toast.LENGTH_SHORT).show()
            }
        })
        CometChatGroupList.setItemClickListener(object : OnItemClickListener<Any>() {
            override fun OnItemClick(item: Any, position: Int) {
                group = item as Group
                if (group!!.isJoined) {
                    startGroupIntent(group)
                } else {
                    if (group!!.groupType == CometChatConstants.GROUP_TYPE_PASSWORD) {
                        val dialogue = layoutInflater.inflate(R.layout.cc_dialog, null)
                        val title = dialogue.findViewById<TextView>(R.id.textViewDialogueTitle)
                        title.text = ""
                        CustomAlertDialogHelper(
                            requireContext(), "Password", dialogue, "Join",
                            "", "Cancel", this@ChatsFragment, 1, false
                        )
                    } else if (group!!.groupType == CometChatConstants.GROUP_TYPE_PUBLIC) {
                        joinGroup(group)
                    }
                }
            }
        })
        CometChatConversationList.setItemClickListener(object : OnItemClickListener<Any>() {
            override fun OnItemClick(t: Any, position: Int) {
                val conversation = t as Conversation
                if (conversation.conversationType == CometChatConstants.CONVERSATION_TYPE_GROUP) {
                    startGroupIntent(conversation.conversationWith as Group)
                } else {
                    val user = conversation.conversationWith as User
                    Toast.makeText(requireContext(), user.name, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun loadFragment(fragment: Fragment?) {
        if (fragment != null) {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.frame, fragment).commit()
        }
    }

    private fun startGroupIntent(group: Group?) {
        val intent = Intent(requireContext(), CometChatMessageListActivity::class.java)
        intent.putExtra(UIKitConstants.IntentStrings.GUID, group!!.guid)
        intent.putExtra(UIKitConstants.IntentStrings.GROUP_OWNER, group.owner)
        intent.putExtra(UIKitConstants.IntentStrings.AVATAR, group.icon)
        intent.putExtra(UIKitConstants.IntentStrings.NAME, group.name)
        intent.putExtra(UIKitConstants.IntentStrings.GROUP_TYPE, group.groupType)
        intent.putExtra(UIKitConstants.IntentStrings.TYPE, CometChatConstants.RECEIVER_TYPE_GROUP)
        intent.putExtra(UIKitConstants.IntentStrings.MEMBER_COUNT, group.membersCount)
        intent.putExtra(UIKitConstants.IntentStrings.GROUP_DESC, group.description)
        intent.putExtra(UIKitConstants.IntentStrings.GROUP_PASSWORD, group.password)
        startActivity(intent)
    }

    private fun joinGroup(group: Group?) {
        progressDialog = ProgressDialog.show(requireContext(), "", "Joining")
        progressDialog!!.setCancelable(false)
        CometChat.joinGroup(
            group!!.guid,
            group.groupType,
            groupPassword,
            object : CometChat.CallbackListener<Group?>() {
                override fun onSuccess(group: Group?) {
                    if (progressDialog != null) {
                        progressDialog!!.dismiss()
                    }
                    startGroupIntent(group)
                }

                override fun onError(e: CometChatException) {
                    if (progressDialog != null) {
                        progressDialog!!.dismiss()
                    }
                    ErrorMsgUtils.showChatErrorMessage(requireContext(), e.code)
                }
            })
    }

    override fun onButtonClick(alertDialog: AlertDialog?, v: View?, which: Int, popupId: Int) {
        val groupPasswordInput = v!!.findViewById<View>(R.id.edittextDialogueInput) as EditText
        if (which == DialogInterface.BUTTON_NEGATIVE) {
            alertDialog!!.dismiss()
        } else if (which == DialogInterface.BUTTON_POSITIVE) {
            try {
                progressDialog = ProgressDialog.show(requireContext(), "", "Joining")
                progressDialog!!.setCancelable(false)
                groupPassword = groupPasswordInput.text.toString()
                if (groupPassword!!.isEmpty()) {
                    groupPasswordInput.setText("")
                    groupPasswordInput.error = "Incorrect"
                } else {
                    try {
                        alertDialog!!.dismiss()
                        joinGroup(group)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}