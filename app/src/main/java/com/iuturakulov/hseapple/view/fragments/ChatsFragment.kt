package com.iuturakulov.hseapple.view.fragments

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.ProgressDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import com.cometchat.pro.constants.CometChatConstants
import com.cometchat.pro.core.AppSettings
import com.cometchat.pro.core.CometChat
import com.cometchat.pro.exceptions.CometChatException
import com.cometchat.pro.models.Conversation
import com.cometchat.pro.models.Group
import com.cometchat.pro.models.User
import com.cometchat.pro.uikit.ui_components.calls.call_manager.listener.CometChatCallListener
import com.cometchat.pro.uikit.ui_components.chats.CometChatConversationList
import com.cometchat.pro.uikit.ui_components.groups.group_list.CometChatGroupList
import com.cometchat.pro.uikit.ui_components.messages.message_list.CometChatMessageListActivity
import com.cometchat.pro.uikit.ui_components.users.user_list.CometChatUserList
import com.cometchat.pro.uikit.ui_resources.constants.UIKitConstants
import com.cometchat.pro.uikit.ui_resources.utils.ErrorMessagesUtils
import com.cometchat.pro.uikit.ui_resources.utils.custom_alertDialog.CustomAlertDialogHelper
import com.cometchat.pro.uikit.ui_resources.utils.custom_alertDialog.OnAlertDialogButtonClickListener
import com.cometchat.pro.uikit.ui_resources.utils.item_clickListener.OnItemClickListener
import com.cometchat.pro.uikit.ui_settings.UIKitSettings
import com.iuturakulov.hseapple.R
import com.iuturakulov.hseapple.utils.API_COUNTRY
import com.iuturakulov.hseapple.utils.APP_ID
import com.iuturakulov.hseapple.utils.AUTH_KEY
import com.iuturakulov.hseapple.view.activities.App
import com.iuturakulov.hseapple.view.activities.MainActivity

class ChatsFragment : Fragment(), OnAlertDialogButtonClickListener {

    companion object {
        const val TAG = "ChatsFragment"
    }

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
        val appSettings = AppSettings.AppSettingsBuilder().subscribePresenceForAllUsers().setRegion(
            API_COUNTRY
        ).build()
        CometChat.init(requireContext(), APP_ID, appSettings, object : CometChat.CallbackListener<String>() {
            override fun onSuccess(s: String) {
                UIKitSettings.setAppID(APP_ID)
                UIKitSettings.setAuthKey(AUTH_KEY)
                CometChat.setSource("ui-kit", "android", "kotlin")
                Log.d(TAG, "onSuccess: $s")
            }

            override fun onError(e: CometChatException) {
            }
        })
        val uiKitSettings = UIKitSettings(requireContext())
        uiKitSettings.addConnectionListener(TAG)
        CometChatCallListener.addCallListener(TAG, requireContext())
        createNotificationChannel()
    }

    override fun onDestroy() {
        super.onDestroy()
        CometChatCallListener.removeCallListener(TAG)
        CometChat.removeConnectionListener(TAG)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        val name: CharSequence = getString(R.string.app_name)
        val description = "Description"
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel("2", name, importance)
        channel.description = description
        // Register the channel with the system; you can't change the importance
        // or other notification behaviors after this
        val notificationManager = requireActivity().getSystemService(NotificationManager::class.java)
        notificationManager.createNotificationChannel(channel)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadFragment(CometChatConversationList())
        CometChatUserList.setItemClickListener(object : OnItemClickListener<Any>() {
            override fun OnItemClick(t: Any, position: Int) {
                Toast.makeText(requireContext(), (t as User).name, Toast.LENGTH_SHORT).show()
            }
        })
        CometChatGroupList.setItemClickListener(object : OnItemClickListener<Any>() {
            override fun OnItemClick(t: Any, position: Int) {
                group = t as Group
                if (group!!.isJoined) {
                    startGroupIntent(group)
                } else {
                    if (group!!.groupType == CometChatConstants.GROUP_TYPE_PASSWORD) {
                        val dialogue = layoutInflater.inflate(R.layout.cc_dialog, null)
                        val tvTitle = dialogue.findViewById<TextView>(R.id.textViewDialogueTitle)
                        tvTitle.text = ""
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
                    ErrorMessagesUtils.cometChatErrorMessage(requireContext(), e.code)
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