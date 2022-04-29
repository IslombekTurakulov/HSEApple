package com.iuturakulov.hseapple.view.activities

import android.content.DialogInterface
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.iuturakulov.hseapple.R
import com.iuturakulov.hseapple.model.api.PostEntity
import com.iuturakulov.hseapple.utils.*
import kotlinx.android.synthetic.main.activity_create_group_chat.*
import kotlinx.android.synthetic.main.fragment_news.*
import kotlinx.android.synthetic.main.fragment_news_info.*


class NewsInfoActivity : AppCompatActivity() {

    companion object {
        lateinit var news: PostEntity
    }

    private var editMode: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_news_info)
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        news = postInfo
        topAppBar.title = news.title
        if (role != RoleOfUsers.TEACHER) {
            topAppBar.menu.getItem(R.id.edit_event_menu).isVisible = false
            topAppBar.menu.getItem(R.id.delete_event_menu).isVisible = false
        }
        newsTitleItem.setText(news.title)
        newsDescriptionItem.setText(news.content)
        fieldOptionsInitializer(false)
        imageNewsItem.setImageBitmap(news.mediaLink?.let { decodeString(it) })
        newsToolBar.setNavigationOnClickListener { onBackPressed() }
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

    override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.edit_event_menu -> {
                fieldOptionsInitializer(true)
            }
            R.id.delete_event_menu -> {
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
        return super.onOptionsItemSelected(menuItem)
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
        textInputLayoutTitle.isCounterEnabled = flag
        confirmButtonEdit.visibility = if (flag) View.VISIBLE else View.GONE
    }
}