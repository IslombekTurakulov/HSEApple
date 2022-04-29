package com.iuturakulov.hseapple.view.activities

import android.app.Activity
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.github.dhaval2404.imagepicker.ImagePicker
import com.iuturakulov.hseapple.R
import com.iuturakulov.hseapple.utils.AUTH
import com.iuturakulov.hseapple.utils.validateDescription
import com.iuturakulov.hseapple.utils.validateTitle
import kotlinx.android.synthetic.main.activity_create_group_chat.*

class CreateGroupChatActivity : AppCompatActivity(R.layout.activity_create_group_chat) {

    companion object {
        const val CAMERA_REQUEST_CODE = 100
        const val STORAGE_REQUEST_CODE = 100
    }

    private lateinit var cameraPermissions: Array<String>
    private lateinit var storagePermission: Array<String>

    private lateinit var imageUri: Uri
    lateinit var actionBar: ActionBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_group_chat)
        actionBar = supportActionBar!!
        actionBar.setDisplayShowHomeEnabled(true)
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.title = "Create Group"
        cameraPermissions = arrayOf(
            android.Manifest.permission.CAMERA,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        storagePermission = arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)

        setListeners()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }


    private fun setListeners() {
        createGroupButton.setOnClickListener {
            if (validateInputFields()) {
                if (imageUri.path == null) {
                    // createGroup()
                }
                Toast.makeText(this, "Success", Toast.LENGTH_LONG).show()
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

    private fun createGroup() {
        val hashMap: HashMap<String, String> = HashMap()
        val time = System.currentTimeMillis().toString()
        hashMap["groupId"] = time
        hashMap["groupTitle"] = groupTitleEditText.text.toString()
        hashMap["groupDescription"] = groupDescriptionEditText.text.toString()
        hashMap["groupIcon"] = imageUri.toString()
        hashMap["timestamp"] = time
        hashMap["createdBy"] = AUTH.getClientId()

    }

    private fun checkStoragePermissions(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) == (PackageManager.PERMISSION_GRANTED)
    }

    private fun requestStoragePermissions() {
        ActivityCompat.requestPermissions(this, storagePermission, STORAGE_REQUEST_CODE)
    }

    private fun checkCameraPermissions(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            android.Manifest.permission.CAMERA
        ) == (PackageManager.PERMISSION_GRANTED) && checkStoragePermissions()
    }

    private fun requestCameraPermissions() {
        ActivityCompat.requestPermissions(this, cameraPermissions, CAMERA_REQUEST_CODE)
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
                    //Image Uri will not be null for RESULT_OK
                    val fileUri = data?.data!!
                    imageUri = fileUri
                    groupChatAvatar.setImageURI(fileUri)
                }
                ImagePicker.RESULT_ERROR -> {
                    Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
                }
                else -> {
                    Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show()
                }
            }
        }


    private fun validateInputFields(): Boolean {
        if (!groupTitleEditText.validateTitle()) {
            groupTitleEditText.error = "Incorrect title field"
            return false
        }
        if (!groupDescriptionEditText.validateDescription()) {
            groupDescriptionEditText.error = "Incorrect description field"
            return false
        }
        return true
    }
}