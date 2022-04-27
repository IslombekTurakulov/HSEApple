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
import com.google.firebase.firestore.FirebaseFirestore
import com.iuturakulov.hseapple.R
import com.iuturakulov.hseapple.model.models.News
import com.iuturakulov.hseapple.utils.CourseSelection
import com.iuturakulov.hseapple.utils.SELECTION
import com.iuturakulov.hseapple.utils.validateNews
import com.iuturakulov.hseapple.utils.validateTitle
import com.iuturakulov.hseapple.view.activities.CreateGroupChatActivity
import kotlinx.android.synthetic.main.activity_create_group_chat.*
import kotlinx.android.synthetic.main.fragment_create_news.*
import timber.log.Timber
import java.io.ByteArrayOutputStream
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
        val list: ArrayList<News> = arrayListOf(
            News(
                textNewsEditCreate.text.toString(),
                newsEditDescriptionCreate.text.toString(),
                image = if (imageUri.path == null) {
                    resources.getDrawable(R.drawable.good_night_img).toString()
                } else {
                    encodeUri(imageUri)
                }
            )
        )
        val course =
            if (SELECTION == CourseSelection.CHOSEN_SECOND) "second_course" else "third_course"
        val db = FirebaseFirestore.getInstance().document(course)
        db.collection("news")
            .add(list)
            .addOnSuccessListener { documentReference ->
                Timber.d("DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Timber.w("Error adding document")
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