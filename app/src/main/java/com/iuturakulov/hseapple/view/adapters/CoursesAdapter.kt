package com.iuturakulov.hseapple.view.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.iuturakulov.hseapple.R
import com.iuturakulov.hseapple.model.models.Courses
import com.iuturakulov.hseapple.utils.*
import com.iuturakulov.hseapple.view.activities.MainActivity
import com.iuturakulov.hseapple.view.adapters.CoursesAdapter.DataViewHolder
import kotlinx.android.synthetic.main.component_list_course.view.*
import timber.log.Timber


class CoursesAdapter(
    private var courses: ArrayList<Courses>
) : RecyclerView.Adapter<DataViewHolder>() {

    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(itemNews: Courses) {
            itemView.nameOfCourseField.text = itemNews.nameOfCourse
            if (itemNews.image != null) {
                Glide.with(itemView.courseImage.context)
                    .load(itemNews.image)
                    .into(itemView.courseImage)
            } else {
                itemView.courseImage.setImageDrawable(itemView.resources.getDrawable(R.drawable.app_logo))
            }
        }
    }

    fun updateData(users: ArrayList<Courses>?) {
        if (users != null) {
            courses = users
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        DataViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.component_list_course, parent,
                false
            )
        )

    override fun getItemCount(): Int = courses.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(courses[position])
        val db = FirebaseFirestore.getInstance().document("requests")
        initializeData(db, holder)
        holder.itemView.courseButton.setOnClickListener {
            if (holder.itemView.courseButton.tooltipText == null) {
                val array: ArrayList<String> = arrayListOf()
                val boolArray: ArrayList<Boolean> = arrayListOf()
                if (holder.itemView.nameOfCourseField.text != holder.itemView.resources.getString(R.string.second_course)) {
                    boolArray.add(true)
                }
                boolArray.add(true)
                array.add(holder.itemView.nameOfCourseField.text.toString())
                val user = hashMapOf(
                    KEY_NAME to USER.fullName,
                    KEY_EMAIL to USER.email,
                    KEY_USER_ID to USER.clientId,
                    "accepted" to boolArray,
                    "course_name" to array
                )
                db.collection("courses")
                    .add(user)
                    .addOnSuccessListener { documentReference ->
                        Timber.d("DocumentSnapshot added with ID: ${documentReference.id}")
                    }
                    .addOnFailureListener { e ->
                        Timber.w("Error adding document")
                    }
            } else {
                SELECTION =
                    if (courses[position].nameOfCourse == holder.itemView.resources.getString(R.string.second_course)) {
                        CourseSelection.CHOSEN_SECOND
                    } else {
                        CourseSelection.CHOSEN_THIRD
                    }
                val intent = Intent(holder.itemView.context, MainActivity::class.java)
                holder.itemView.context.startActivity(intent)
                Toast.makeText(holder.itemView.context, "Loading", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun initializeData(
        db: DocumentReference,
        holder: DataViewHolder
    ) {
        db.collection("courses")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    if (document.data.containsValue(USER.email)) {
                        holder.itemView.courseButton.text =
                            holder.itemView.resources.getString(R.string.course_navigate)
                        holder.itemView.courseButton.tooltipText =
                            holder.itemView.resources.getString(R.string.course_navigate)
                        val array: ArrayList<String> =
                            document.data.getValue("course_name") as ArrayList<String>;
                        val boolArray: ArrayList<Boolean> =
                            document.data.getValue("accepted") as ArrayList<Boolean>;
                        if (array.contains(holder.itemView.resources.getString(R.string.second_course))) {
                            boolArray[0] = true
                            array.remove(holder.itemView.resources.getString(R.string.second_course))
                            addUpdatedData(array, document, boolArray, db)
                        }
                        if (array.contains(holder.itemView.resources.getString(R.string.third_course))) {
                            boolArray[1] = true
                            array.remove(holder.itemView.resources.getString(R.string.third_course))
                            addUpdatedData(array, document, boolArray, db)
                        }
                    }
                    Timber.d("${document.id} => ${document.data}")
                }
            }
            .addOnFailureListener {
                Timber.w("Error getting documents.")
            }
    }

    private fun addUpdatedData(
        array: ArrayList<String>,
        document: QueryDocumentSnapshot,
        boolArray: ArrayList<Boolean>,
        db: DocumentReference
    ) {
        val user = hashMapOf(
            KEY_NAME to document.data.getValue(KEY_NAME),
            KEY_EMAIL to document.data.getValue(KEY_EMAIL),
            KEY_USER_ID to document.data.getValue(KEY_USER_ID),
            "accepted" to boolArray,
            "course_name" to array
        )
        db.collection("courses")
            .add(user)
            .addOnSuccessListener { documentReference ->
                Timber.d("DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Timber.w("Error adding document")
            }
    }

    fun addData(list: List<Courses>) {
        courses.addAll(list)
    }

}