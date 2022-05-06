package com.iuturakulov.hseapple.view.activities

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.iuturakulov.hseapple.R
import com.iuturakulov.hseapple.utils.*
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog
import kotlinx.android.synthetic.main.activity_create_tests.*
import kotlinx.android.synthetic.main.toolbar_create_tests.*
import okhttp3.*
import java.io.IOException
import java.util.*


class CreateTestsActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener,
    TimePickerDialog.OnTimeSetListener {

    private lateinit var timePicker: String
    private lateinit var datePicker: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_tests)
        setListeners()
    }

    private fun setListeners() {
        tests_info.text = getString(R.string.create_test_info)
        edit_things_layout.visibility = View.INVISIBLE
        changeDateTimeDeadline.setOnClickListener {
            val now: Calendar = Calendar.getInstance()
            val dpd: DatePickerDialog = DatePickerDialog.newInstance(
                this@CreateTestsActivity,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
            )
            dpd.isThemeDark = true;
            dpd.show(supportFragmentManager, "Datepickerdialog")
            dpd.setOnDismissListener {
                val tpd: TimePickerDialog = TimePickerDialog.newInstance(
                    this@CreateTestsActivity,
                    now.get(Calendar.HOUR),
                    now.get(Calendar.MINUTE),
                    now.get(Calendar.SECOND),
                    true
                )
                tpd.isThemeDark = true;
                tpd.show(supportFragmentManager, "Timepickerdialog")
                tpd.setOnDismissListener {
                    dateTimeOfDeadline.text = "$datePicker $timePicker"
                }
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
                  "courseID": ${if (SELECTION == CourseSelection.CHOSEN_SECOND) 1 else 2},
                  "form": "lab",
                  "title": "${createTextTitleTask.text}",
                  "description": "${createTextDescTask.text}",
                  "task_content": "Задание",
                  "deadline": "${dateTimeOfDeadline.text}",
                  "status": false
                }"""
        )
        val request = Request.Builder()
            .url("${IP_ADDRESS}/course/task")
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
        when {
            responseGet != null && responseGet.isSuccessful -> {
                Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
            }
            else -> {
                Toast.makeText(this, "Failure", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onTimeSet(view: TimePickerDialog?, hourOfDay: Int, minute: Int, second: Int) {
        timePicker = "$hourOfDay:$minute:$second"
    }

    override fun onDateSet(view: DatePickerDialog?, year: Int, monthOfYear: Int, dayOfMonth: Int) {
        datePicker = "$year-$dayOfMonth-${monthOfYear + 1}"
    }
}