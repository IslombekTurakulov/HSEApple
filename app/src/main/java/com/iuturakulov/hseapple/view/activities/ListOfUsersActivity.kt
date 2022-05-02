package com.iuturakulov.hseapple.view.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.iuturakulov.hseapple.databinding.ActivityListOfUsersBinding

class ListOfUsersActivity : AppCompatActivity() {
    private lateinit var binding: ActivityListOfUsersBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityListOfUsersBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

    }
}