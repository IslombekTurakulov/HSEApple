package com.iuturakulov.hseapple.view.activities

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.datetime.dateTimePicker
import com.afollestad.materialdialogs.lifecycle.lifecycleOwner
import com.iuturakulov.hseapple.R
import com.iuturakulov.hseapple.utils.*
import kotlinx.android.synthetic.main.activity_create_tests.*
import kotlinx.android.synthetic.main.toolbar_create_tests.*
import okhttp3.*
import java.io.IOException

class CreateTestsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_tests)
        setListeners()
    }

    private fun setListeners() {
        tests_info.text = getString(R.string.create_test_info)
        edit_things_layout.visibility = View.INVISIBLE
        changeDateTimeDeadline.onClickDebounced {
            MaterialDialog(this).show {
                title(text = "Select Date and Time")
                dateTimePicker(requireFutureDateTime = true) { _, dateTime ->
                    toast("Selected date/time: ${dateTime.formatDateTime()}")
                    dateTimeOfDeadline.text = dateTime.formatDateTime()
                }
                lifecycleOwner(APP_ACTIVITY)
            }
        }
        create_task_button.setOnClickListener {
            if (validateInputFields()) {
                if (!dateTimeOfDeadline.text.isNullOrEmpty()) {
                    createTest()
                    finish()
                } else {
                    toast("Please select correct datetime!")
                }
            }
        }
    }

    private fun validateInputFields(): Boolean {
        if (!createTextTitleTask.validateTitle()) {
            createTextTitleTask.error = "Incorrect title field"
            return false
        }
        if (!createTextDescTask.validateNews()) {
            createTextDescTask.error = "Incorrect description field"
            return false
        }
        return true
    }

    private fun createTest() {
        val client = OkHttpClient().newBuilder()
            .build()
        val mediaType = MediaType.parse("application/json")
        val body = RequestBody.create(
            mediaType,
            """{
                  "form": "lab",
                  "title": "${createTextTitleTask.text}",
                  "description": "${createTextDescTask.text}",
                  "taskContent": "Задание",
                  "deadline": "${dateTimeOfDeadline.text}",
                  "status": false
                }"""
        )
        val request = Request.Builder()
            .url("http://80.66.64.53:8080/course/${if (SELECTION == CourseSelection.CHOSEN_SECOND) 1 else 2}/task")
            .method("POST", body)
            .addHeader(
                "Authorization",
                TEMP_TOKEN
            )
            .addHeader("Content-Type", "application/json")
            .addHeader("Cookie", "JSESSIONID=53530B6092B00A54239E5E86BAEE3EE6")
            .build()
        var responseGet: Response? = null
        try {
            responseGet = client.newCall(request).execute()
        } catch (e: IOException) {
            e.printStackTrace();
        }
        if (responseGet != null) {
            Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Failure", Toast.LENGTH_SHORT).show()
        }
    }
}