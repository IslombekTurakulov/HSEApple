package com.iuturakulov.hseapple.view.fragments

import android.app.Activity
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.gson.Gson
import com.iuturakulov.hseapple.R
import com.iuturakulov.hseapple.model.api.PostEntity
import com.iuturakulov.hseapple.utils.TOKEN_API
import com.iuturakulov.hseapple.utils.asDateTime
import com.iuturakulov.hseapple.utils.validateNews
import com.iuturakulov.hseapple.utils.validateTitle
import com.iuturakulov.hseapple.view.activities.CreateGroupChatActivity
import com.squareup.okhttp.*
import kotlinx.android.synthetic.main.activity_create_group_chat.*
import kotlinx.android.synthetic.main.fragment_create_news.*
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.time.LocalDateTime
import java.util.*


class CreateNewsFragment : Fragment(R.layout.fragment_create_news) {

    companion object {
        const val CAMERA_REQUEST_CODE = 100
        const val STORAGE_REQUEST_CODE = 100
    }

    private lateinit var cameraPermissions: Array<String>
    private lateinit var storagePermission: Array<String>

    private lateinit var imageUri: Uri

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        setListeners()
        return inflater.inflate(R.layout.fragment_create_news, container, false)
    }

    private fun setListeners() {
        createGroupButton.setOnClickListener {
            if (validateInputFields()) {
                createEvent()
                Toast.makeText(requireContext(), "Success", Toast.LENGTH_LONG).show()
            }
        }
        groupChatAvatar.setOnClickListener {
            if (!checkCameraPermissions()) {
                requestCameraPermissions()
            }
            if (!checkStoragePermissions()) {
                requestStoragePermissions()
            }
            showImagePickerDialog()
        }
    }


    private fun createEvent() {
        val client = OkHttpClient()
        val requestGet = Request.Builder()
            .url("https://stoplight.io/mocks/hseapple/nis-app/38273133/course/0/post?start=0")
            .get()
            .addHeader("Content-Type", "application/json")
            .addHeader("token", TOKEN_API)
            .build()
        var responsePost: Response? = null
        try {
            responsePost = client.newCall(requestGet).execute()
        } catch (e: IOException) {
            e.printStackTrace();
        }
        var res: Array<PostEntity>? = null
        if (responsePost != null) {
            res =
                Gson().fromJson(
                    responsePost.body().string(),
                    Array<PostEntity>::class.java
                )
        }
        val mediaType = MediaType.parse("application/json")
        val body = RequestBody.create(
            mediaType,
            """{
          "courseId": ${if (!res.isNullOrEmpty()) res.size - 1 else 0},
          "title": "${textNewsEditCreate.text.toString()}",
          "content": "${groupDescriptionEditText.text.toString()}",
          "media_link": "${encodeUri(imageUri)}",
          "createdAt": "${LocalDateTime.now().toString().asDateTime()}"
        }"""
        )
        val requestPost = Request.Builder()
            .url(
                "https://stoplight.io/mocks/hseapple/nis-app/38273133/course/${
                    if (!res.isNullOrEmpty()) res[res.size - 1].courseid?.plus(
                        1
                    ) else 0
                }/post"
            )
            .post(body)
            .addHeader("Content-Type", "application/json")
            .addHeader("token", TOKEN_API)
            .build()
        var responseGet: Response? = null
        try {
            responseGet = client.newCall(requestPost).execute()
        } catch (e: IOException) {
            e.printStackTrace();
        }
        if (responseGet != null) {
            println(responseGet.body().toString())
        }
    }

    private fun encodeUri(imageUri: Uri): String {
        val input = requireActivity().contentResolver.openInputStream(imageUri)
        val baos = ByteArrayOutputStream()
        BitmapFactory.decodeStream(input, null, null)
            ?.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        return Base64.getEncoder().encodeToString(baos.toByteArray())
    }


    private fun checkStoragePermissions(): Boolean {
        return ContextCompat.checkSelfPermission(
            requireContext(),
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) == (PackageManager.PERMISSION_GRANTED)
    }

    private fun requestStoragePermissions() {
        ActivityCompat.requestPermissions(
            requireActivity(), storagePermission,
            CreateGroupChatActivity.STORAGE_REQUEST_CODE
        )
    }

    private fun checkCameraPermissions(): Boolean {
        return ContextCompat.checkSelfPermission(
            requireContext(),
            android.Manifest.permission.CAMERA
        ) == (PackageManager.PERMISSION_GRANTED) && checkStoragePermissions()
    }

    private fun requestCameraPermissions() {
        ActivityCompat.requestPermissions(
            requireActivity(), cameraPermissions,
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
                    Toast.makeText(requireContext(), ImagePicker.getError(data), Toast.LENGTH_SHORT)
                        .show()
                }
                else -> {
                    Toast.makeText(requireContext(), "Task Cancelled", Toast.LENGTH_SHORT).show()
                }
            }
        }


    private fun validateInputFields(): Boolean {
        if (!textNewsEditCreate.validateTitle()) {
            textNewsEditCreate.error = "Incorrect title field"
            return false
        }
        if (!newsEditDescriptionCreate.validateNews()) {
            newsEditDescriptionCreate.error = "Incorrect description field"
            return false
        }
        return true
    }
}