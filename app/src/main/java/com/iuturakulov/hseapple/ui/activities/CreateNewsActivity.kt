package com.iuturakulov.hseapple.ui.activities

import android.app.Activity
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.github.dhaval2404.imagepicker.ImagePicker
import com.iuturakulov.hseapple.R
import com.iuturakulov.hseapple.api.OkHttpInstance
import com.iuturakulov.hseapple.utils.*
import kotlinx.android.synthetic.main.activity_create_news.*
import kotlinx.android.synthetic.main.toolbar_news_info.*
import okhttp3.MediaType
import okhttp3.RequestBody
import okhttp3.Response
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.util.*


class CreateNewsActivity : AppCompatActivity(R.layout.activity_create_news) {

    private lateinit var cameraPermissions: Array<String>
    private lateinit var storagePermission: Array<String>

    private var imageUri: Uri = Uri.EMPTY

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_news)
        setListeners()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setListeners() {
        edit_things_layout.visibility = View.GONE
        news_info.text = getString(R.string.news_menu_text)
        back_arrow_news.setOnClickListener {
            onBackPressed()
        }
        create_event_button.setOnClickListener {
            if (validateInputFields()) {
                createEvent()
                finish()
            }
        }
        newsImageCreate.setOnClickListener {
            if (!checkCameraPermissions()) {
                requestCameraPermissions()
            }
            if (!checkStoragePermissions()) {
                requestStoragePermissions()
            }
            showImagePickerDialog()
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun createEvent() {
        val mediaType = MediaType.parse("application/json")
        val body = RequestBody.create(
            mediaType,
            """{
          "courseID": ${if (SELECTION == CourseSelection.CHOSEN_SECOND) 1 else 2},
          "title": "${createTextTitleNews.text.toString()}",
          "content": "${createTextDescNews.text.toString()}",
          "mediaLink": "${if (!imageUri.path.isNullOrEmpty()) encodeUri(imageUri) else null}"
        }"""
        )
        var responseGet: Response? = null
        try {
            responseGet =
                OkHttpInstance.getInstance()
                    .newCall(OkHttpInstance.postRequest("course/post", body, TEMP_TOKEN))
                    .execute()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        if (responseGet != null) {
            Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Failure", Toast.LENGTH_SHORT).show()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun encodeUri(imageUri: Uri): String {
        val input = contentResolver.openInputStream(imageUri)
        val baos = ByteArrayOutputStream()
        BitmapFactory.decodeStream(input, null, null)
            ?.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val result = Base64.getEncoder().encodeToString(baos.toByteArray())
        Toast.makeText(this, result, Toast.LENGTH_SHORT).show()
        return result
    }


    private fun checkStoragePermissions(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) == (PackageManager.PERMISSION_GRANTED)
    }

    private fun requestStoragePermissions() {
        ActivityCompat.requestPermissions(
            this, storagePermission,
            CreateGroupChatActivity.STORAGE_REQUEST_CODE
        )
    }

    private fun checkCameraPermissions(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            android.Manifest.permission.CAMERA
        ) == (PackageManager.PERMISSION_GRANTED) && checkStoragePermissions()
    }

    private fun requestCameraPermissions() {
        ActivityCompat.requestPermissions(
            this, cameraPermissions,
            CreateGroupChatActivity.CAMERA_REQUEST_CODE
        )
    }

    private fun showImagePickerDialog() {
        ImagePicker.with(this)
            .crop()
            .galleryMimeTypes(  //Exclude gif images
                mimeTypes = arrayOf(
                    "image/png",
                    "image/jpg",
                    "image/jpeg"
                )
            )
            .compress(1024)
            .maxResultSize(1080, 1080)
            .createIntent { intent ->
                startForProfileImageResult.launch(intent)
            }

    }

    private val startForProfileImageResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val resultCode = result.resultCode
            val data = result.data
            when (resultCode) {
                Activity.RESULT_OK -> {
                    val fileUri = data?.data!!
                    imageUri = fileUri
                    newsImageCreate.setImageURI(fileUri)
                }
                ImagePicker.RESULT_ERROR -> {
                    Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT)
                        .show()
                }
                else -> {
                    Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show()
                }
            }
        }


    private fun validateInputFields(): Boolean {
        if (!createTextTitleNews.validateTitle()) {
            createTextTitleNews.error = "Incorrect title field"
            return false
        }
        if (!createTextDescNews.validateNews()) {
            createTextDescNews.error = "Incorrect description field"
            return false
        }
        return true
    }
}