package com.iuturakulov.hseapple.ui.activities

import android.content.DialogInterface
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.iuturakulov.hseapple.R
import com.iuturakulov.hseapple.api.OkHttpInstance
import com.iuturakulov.hseapple.utils.*
import kotlinx.android.synthetic.main.activity_news_info.*
import kotlinx.android.synthetic.main.toolbar_news_info.*
import okhttp3.MediaType
import okhttp3.RequestBody
import okhttp3.Response
import java.io.IOException
import java.util.*


class NewsInfoActivity : AppCompatActivity() {

    private var editMode: Boolean = false

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_info)
        initializeTopAppBar()
        initializeFields()
    }

    private fun initializeTopAppBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        news_info.text = postInfo.title
        if (USER_CHAT?.role != "teacher") {
            edit_things_layout.visibility = View.INVISIBLE
        } else {
            edit_things_layout.visibility = View.VISIBLE
        }
        back_arrow_news.setOnClickListener {
            onBackPressed()
        }
        edit_event.setOnClickListener {
            fieldOptionsInitializer(true)
        }
        delete_event.setOnClickListener {
            val dialogClickListener: DialogInterface.OnClickListener =
                DialogInterface.OnClickListener { dialog, which ->
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

    @RequiresApi(Build.VERSION_CODES.O)
    private fun initializeFields() {
        newsTitleItem.setText(postInfo.title)
        newsDescriptionItem.setText(postInfo.content)
        fieldOptionsInitializer(false)
        if (postInfo.mediaLink.isNullOrEmpty()) {
            imageNewsItem.setImageDrawable(getDrawable(com.hse.auth.R.drawable.settings))
        } else {
            val bytes: ByteArray = Base64.getDecoder().decode(postInfo.mediaLink)
            val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
            imageNewsItem.setImageBitmap(bitmap)
        }
        confirmButtonEdit.setOnClickListener {
            if (validateInputFields()) {
                editDataNews()
                Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
                fieldOptionsInitializer(false)
            }
        }
        "${getString(R.string.created_time)} ${if (postInfo.updatedAt == null) postInfo.createdAt.toString() else postInfo.updatedAt.toString()}".also {
            dateTimeNewsItem.text = it
        }
    }

    private fun editDataNews() {
        val mediaType = MediaType.parse("application/json")
        val body = RequestBody.create(
            mediaType,
            """{
          "courseID": ${postInfo.courseid},
          "title": "${postInfo.title}",
          "content": "${postInfo.content}",
          "mediaLink": "${postInfo.mediaLink}"
        }"""
        )
        var responseGet: Response? = null
        try {
            responseGet = OkHttpInstance.getInstance()
                .newCall(OkHttpInstance.postRequest("course/post", body, TEMP_TOKEN)).execute()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        when {
            responseGet != null -> {
                Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
            }
            else -> {
                Toast.makeText(this, "Failure", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun validateInputFields(): Boolean {
        if (!newsTitleItem.validateTitle()) {
            newsTitleItem.error = "Incorrect title field"
            return false
        }
        if (!newsDescriptionItem.validateNews()) {
            newsDescriptionItem.error = "Incorrect description field"
            return false
        }
        return true
    }

    private fun fieldOptionsInitializer(flag: Boolean) {
        editMode = flag
        newsTitleItem.isClickable = flag
        newsDescriptionItem.isClickable = flag
        newsTitleItem.isFocusable = flag
        newsDescriptionItem.isFocusable = flag
        newsTitleItem.isFocusableInTouchMode = flag
        newsDescriptionItem.isFocusableInTouchMode = flag
        if (!flag) {
            newsTitleItem.tag = newsTitleItem.keyListener
            newsDescriptionItem.tag = newsDescriptionItem.keyListener
        }
        textInputLayoutDesc.isCounterEnabled = flag
        textNewsTitleCreate.isCounterEnabled = flag
        confirmButtonEdit.visibility = if (flag) View.VISIBLE else View.GONE
    }
}