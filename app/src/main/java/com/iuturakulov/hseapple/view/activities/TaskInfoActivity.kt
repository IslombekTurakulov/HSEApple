package com.iuturakulov.hseapple.view.activities

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.datetime.dateTimePicker
import com.afollestad.materialdialogs.lifecycle.lifecycleOwner
import com.iuturakulov.hseapple.R
import com.iuturakulov.hseapple.model.api.TaskEntity
import com.iuturakulov.hseapple.utils.*
import com.iuturakulov.hseapple.utils.onClickDebounced
import kotlinx.android.synthetic.main.activity_create_tests.*
import kotlinx.android.synthetic.main.activity_create_tests.changeDateTimeDeadline
import kotlinx.android.synthetic.main.activity_create_tests.create_task_button
import kotlinx.android.synthetic.main.activity_create_tests.dateTimeOfDeadline
import kotlinx.android.synthetic.main.activity_task_info.*
import kotlinx.android.synthetic.main.toolbar_create_tests.*
import okhttp3.*
import java.io.IOException

class TaskInfoActivity : AppCompatActivity() {

    companion object {
        lateinit var taskEntity: TaskEntity
    }

    private var editMode: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_info)
        setListeners()
        initializeTopAppBar()
        urlTextInput
    }

    private fun setListeners() {
        edit_things_layout.visibility = View.INVISIBLE
        changeDateTimeDeadline.onClickDebounced {
            MaterialDialog(this).show {
                title(text = "Select Date and Time")
                dateTimePicker(requireFutureDateTime = true) { _, dateTime ->
                    toast("Selected date/time: ${dateTime.formatDateTime()}")
                    dateTimeOfDeadline.text = dateTime.formatDateTime()
                }
                lifecycleOwner(this@TaskInfoActivity)
            }
        }
        create_task_button.setOnClickListener {
            if (validateInputFields()) {
                if (!dateTimeOfDeadline.text.isNullOrEmpty()) {
                    createTest()
                    fieldOptionsInitializer(false)
                } else {
                    toast("Please select correct datetime!")
                }
            }
        }
        load_task_button.setOnClickListener {
            if (!urlTextInput.text.isNullOrEmpty()) {
                createTest()
            } else {
                urlTextInput.error = "Please type your url here..."
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

    private fun initializeTopAppBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        taskEntity = taskInfo
        tests_info.text = taskEntity.title
        if (USER_CHAT.role != "teacher") {
            edit_things_layout.visibility = View.GONE
        } else {
            edit_things_layout.visibility = View.VISIBLE
        }
        back_arrow_tests.setOnClickListener {
            onBackPressed()
        }
        edit_test.setOnClickListener {
            fieldOptionsInitializer(true)
        }
        delete_test.setOnClickListener {
            val dialogClickListener: DialogInterface.OnClickListener =
                DialogInterface.OnClickListener { _, which ->
                    when (which) {
                        DialogInterface.BUTTON_POSITIVE -> {
                            Toast.makeText(this, "YES", Toast.LENGTH_SHORT).show()
                        }
                        DialogInterface.BUTTON_NEGATIVE -> {
                            Toast.makeText(this, "No", Toast.LENGTH_SHORT).show()
                        }
                    }
                }

            val builder: AlertDialog.Builder = AlertDialog.Builder(this)
            builder.setMessage(getString(R.string.arsure_dialog))
                .setPositiveButton(getString(R.string.yes_status), dialogClickListener)
                .setNegativeButton(getString(R.string.no_status), dialogClickListener).show()
        }
    }

    private fun fieldOptionsInitializer(flag: Boolean) {
        editMode = flag
        createTextTitleTaskInfo.isClickable = flag
        createTextDescTaskInfo.isClickable = flag
        createTextTitleTaskInfo.isFocusable = flag
        createTextDescTaskInfo.isFocusable = flag
        createTextTitleTaskInfo.isFocusableInTouchMode = flag
        createTextDescTaskInfo.isFocusableInTouchMode = flag
        if (!flag) {
            createTextTitleTaskInfo.tag = createTextTitleTaskInfo.keyListener
            createTextDescTaskInfo.tag = createTextDescTaskInfo.keyListener
            deadlineTextInfo.visibility = View.GONE
            changeDateTimeDeadline.visibility = View.VISIBLE
            create_task_button.visibility = View.VISIBLE
            load_task_button.visibility = View.INVISIBLE
            inputLinkLayout.visibility = View.GONE
        } else {
            deadlineTextInfo.visibility = View.VISIBLE
            inputLinkLayout.visibility = View.VISIBLE
            changeDateTimeDeadline.visibility = View.GONE
            create_task_button.visibility = View.INVISIBLE
            load_task_button.visibility = View.VISIBLE
        }
        createTextLayoutTitleTaskInfo.isCounterEnabled = flag
        createTextLayoutDescTaskInfo.isCounterEnabled = flag
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
                  "task_content": "Задание",
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