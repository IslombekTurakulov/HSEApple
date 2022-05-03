package com.iuturakulov.hseapple.view.activities

import android.content.DialogInterface
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.iuturakulov.hseapple.R
import com.iuturakulov.hseapple.model.api.PostEntity
import com.iuturakulov.hseapple.utils.*
import kotlinx.android.synthetic.main.fragment_news_info.*
import kotlinx.android.synthetic.main.toolbar_news_info.*
import okhttp3.OkHttpClient
import okhttp3.Request
import java.util.*


class NewsInfoActivity : AppCompatActivity() {

    companion object {
        lateinit var news: PostEntity
    }

    private var editMode: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_news_info)
        initializeTopAppBar()
        initializeFields()
    }

    override fun onDestroy() {
        super.onDestroy()
        val client = OkHttpClient().newBuilder()
            .build()
        val request = Request.Builder()
            .url("http://80.66.64.53:8080/course/2/post?start=1&limit=10")
            .method("GET", null)
            .addHeader(
                "Authorization",
                TEMP_TOKEN
            )
            .addHeader("Content-Type", "application/json")
            .addHeader("Cookie", "JSESSIONID=53530B6092B00A54239E5E86BAEE3EE6")
            .build()
    }

    private fun initializeTopAppBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        news = postInfo
        news_info.text = news.title
        if (USER_CHAT.role != "teacher") {
            edit_things_layout.visibility = View.GONE
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

    private fun initializeFields() {
        newsTitleItem.setText(news.title)
        newsDescriptionItem.setText(news.content)
        fieldOptionsInitializer(false)
        if (news.mediaLink.isNullOrEmpty()) {
            imageNewsItem.setImageDrawable(getDrawable(R.drawable.good_night_img))
        } else {
            val bytes: ByteArray = Base64.getDecoder().decode(news.mediaLink)
            val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
            imageNewsItem.setImageBitmap(bitmap)
        }
        confirmButtonEdit.setOnClickListener {
            if (validateInputFields()) {
                Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
                fieldOptionsInitializer(false)
            }
        }
        "${getString(R.string.created_time)} ${if (news.updatedAt == null) news.createdAt.toString() else news.updatedAt.toString()}".also {
            dateTimeNewsItem.text = it
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