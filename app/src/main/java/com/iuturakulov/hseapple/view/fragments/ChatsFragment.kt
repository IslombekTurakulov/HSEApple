package com.iuturakulov.hseapple.view.fragments

import android.app.ProgressDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
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
import com.cometchat.pro.uikit.ui_resources.constants.UIKitConstants
import com.cometchat.pro.uikit.ui_resources.utils.ErrorMessagesUtils
import com.cometchat.pro.uikit.ui_resources.utils.custom_alertDialog.CustomAlertDialogHelper
import com.cometchat.pro.uikit.ui_resources.utils.custom_alertDialog.OnAlertDialogButtonClickListener
import com.cometchat.pro.uikit.ui_resources.utils.item_clickListener.OnItemClickListener
import com.iuturakulov.hseapple.R

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
        loadFragment(CometChatConversationList())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadFragment(CometChatConversationList())
        CometChatGroupList.setItemClickListener(object : OnItemClickListener<Any>() {
            override fun OnItemClick(t: Any, position: Int) {
                group = t as Group
                if (group!!.isJoined) {
                    startGroupIntent(group)
                } else {
                    if (group!!.groupType == CometChatConstants.GROUP_TYPE_PASSWORD) {
                        val dialogview = layoutInflater.inflate(R.layout.cc_dialog, null)
                        val tvTitle = dialogview.findViewById<TextView>(R.id.textViewDialogueTitle)
                        tvTitle.text = ""
                        CustomAlertDialogHelper(
                            requireContext(), "Password", dialogview, "Join",
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
                    userIntent(user)
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

    fun userIntent(user: User) {
        val intent = Intent(requireContext(), CometChatMessageListActivity::class.java)
        intent.putExtra(UIKitConstants.IntentStrings.UID, user.uid)
        intent.putExtra(UIKitConstants.IntentStrings.AVATAR, user.avatar)
        intent.putExtra(UIKitConstants.IntentStrings.STATUS, user.status)
        intent.putExtra(UIKitConstants.IntentStrings.NAME, user.name)
        intent.putExtra(UIKitConstants.IntentStrings.TYPE, CometChatConstants.RECEIVER_TYPE_USER)
        startActivity(intent)
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
                    ErrorMessagesUtils.cometChatErrorMessage(requireContext(), e.code)
                }
            })
    }

    override fun onButtonClick(alertDialog: AlertDialog?, v: View?, which: Int, popupId: Int) {
        val groupPasswordInput = v!!.findViewById<View>(R.id.edittextDialogueInput) as EditText
        if (which == DialogInterface.BUTTON_NEGATIVE) { // Cancel
            alertDialog!!.dismiss()
        } else if (which == DialogInterface.BUTTON_POSITIVE) { // Join
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