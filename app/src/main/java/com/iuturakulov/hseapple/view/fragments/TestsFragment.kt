package com.iuturakulov.hseapple.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.iuturakulov.hseapple.R
import com.iuturakulov.hseapple.utils.USER_CHAT
import com.iuturakulov.hseapple.utils.showToast
import kotlinx.android.synthetic.main.fragment_tests.*

class TestsFragment : Fragment(R.layout.fragment_tests) {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tests, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (USER_CHAT.role != "teacher") {
            toolBar_tests.visibility = View.INVISIBLE
        } else {
            toolBar_tests.visibility = View.VISIBLE
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.create_lab -> {
                showToast("Another Menu Item 1 Selected")
            }
            R.id.create_test -> {
                showToast("Another Menu Item 2 Selected")
            }
        }
        return super.onOptionsItemSelected(item)
    }
}