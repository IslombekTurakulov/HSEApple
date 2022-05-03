package com.iuturakulov.hseapple.view.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.cometchat.pro.uikit.ui_components.users.user_list.CometChatUserList
import com.iuturakulov.hseapple.R.id
import com.iuturakulov.hseapple.databinding.ActivityListOfUsersBinding

class ListOfUsersActivity : AppCompatActivity() {
    private lateinit var binding: ActivityListOfUsersBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListOfUsersBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadFragment(CometChatUserList("default"))
    }

    private fun loadFragment(fragment: Fragment?): Boolean {
        if (fragment != null) {
            supportFragmentManager.beginTransaction().replace(id.frameOfUsers, fragment).commit()
            return true
        }
        return false
    }

}