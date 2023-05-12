package com.iuturakulov.hseapple.ui.activities

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.iuturakulov.hseapple.R
import com.iuturakulov.hseapple.api.OkHttpInstance
import com.iuturakulov.domain.entities.TaskEntity
import com.iuturakulov.hseapple.utils.*
import kotlinx.android.synthetic.main.activity_task_info.*
import kotlinx.android.synthetic.main.toolbar_create_tests.*
import okhttp3.MediaType
import okhttp3.RequestBody
import okhttp3.Response
import java.io.IOException

class TaskInfoActivity : AppCompatActivity() {

    companion object {
        lateinit var taskEntity: com.iuturakulov.domain.entities.TaskEntity
    }

    private var editMode: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_info)
        fieldOptionsInitializer(false)
        setListeners()
        initializeTopAppBar()
    }

    private fun setListeners() {
        create_task_button.setOnClickListener {
            if (validateInputFields()) {
                if (!dateTimeOfDeadline.text.isNullOrEmpty()) {
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
        if (!createTextTitleTaskInfo.validateTitle()) {
            createTextTitleTaskInfo.error = "Incorrect title field"
            return false
        }
        if (!createTextDescTaskInfo.validateNews()) {
            createTextDescTaskInfo.error = "Incorrect description field"
            return false
        }
        return true
    }

    private fun initializeTopAppBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        taskEntity = taskInfo
        tests_info.text = taskEntity.title
        createTextTitleTaskInfo.setText(taskEntity.title)
        createTextDescTaskInfo.setText(taskEntity.description)
        dateTimeOfDeadline.text = taskEntity.dueDate.toString()
        if (USER_CHAT!!.role != "teacher") {
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
        if (flag) {
            deadlineTextInfo.visibility = View.VISIBLE
            inputLinkLayout.visibility = View.VISIBLE
            changeDateTimeDeadline.visibility = View.GONE
            create_task_button.visibility = View.INVISIBLE
            load_task_button.visibility = View.VISIBLE
        } else {
            createTextTitleTaskInfo.tag = createTextTitleTaskInfo.keyListener
            createTextDescTaskInfo.tag = createTextDescTaskInfo.keyListener
            deadlineTextInfo.visibility = View.GONE
            changeDateTimeDeadline.visibility = View.VISIBLE
            create_task_button.visibility = View.VISIBLE
            load_task_button.visibility = View.INVISIBLE
            inputLinkLayout.visibility = View.GONE
        }
        createTextLayoutTitleTaskInfo.isCounterEnabled = flag
        createTextLayoutDescTaskInfo.isCounterEnabled = flag
    }

    private fun createTest() {
        val mediaType = MediaType.parse("application/json")
        val body = RequestBody.create(
            mediaType,
            """{
                  "form": "lab",
                  "title": "${createTextTitleTaskInfo.text}",
                  "description": "${createTextDescTaskInfo.text}",
                  "task_content": "Задание",
                  "deadline": "${dateTimeOfDeadline.text}",
                  "status": true
                }"""
        )
        var responseGet: Response? = null
        try {
            responseGet = OkHttpInstance.getInstance().newCall(
                OkHttpInstance.postRequest(
                    "task/${if (SELECTION == CourseSelection.CHOSEN_SECOND) 1 else 2}/answer",
                    body, ACCESS_TOKEN
                )
            ).execute()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        when {
            responseGet != null -> {
                Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
                finish()
            }
            else -> {
                Toast.makeText(this, "Failure", Toast.LENGTH_SHORT).show()
            }
        }
    }
}