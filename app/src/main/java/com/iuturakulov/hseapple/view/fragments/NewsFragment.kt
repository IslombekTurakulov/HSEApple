package com.iuturakulov.hseapple.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.FirebaseFirestore
import com.iuturakulov.hseapple.R
import com.iuturakulov.hseapple.model.models.News
import com.iuturakulov.hseapple.utils.CourseSelection
import com.iuturakulov.hseapple.utils.SELECTION
import com.iuturakulov.hseapple.utils.replaceFragment
import com.iuturakulov.hseapple.utils.showToast
import com.iuturakulov.hseapple.view.adapters.NewsAdapter
import kotlinx.android.synthetic.main.fragment_available_courses.swipe_refresh_layout
import kotlinx.android.synthetic.main.fragment_news.*
import timber.log.Timber

class NewsFragment : Fragment(R.layout.fragment_news) {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateDatabase()
        swipe_refresh_layout.setOnRefreshListener {
            updateDatabase()
            swipe_refresh_layout.isRefreshing = false
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.create_event -> {
                showToast("Create event")
                replaceFragment(CreateNewsFragment(), false)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun updateDatabase() {
        val course =
            if (SELECTION == CourseSelection.CHOSEN_SECOND) "second_course" else "third_course"
        val db = FirebaseFirestore.getInstance().document(course)
        db.collection("news")
            .get()
            .addOnSuccessListener { result ->
                if (result.isEmpty) {
                    Timber.w("Document News is empty!")
                } else {
                    val list: ArrayList<News> =
                        result.toObjects(News::class.java) as ArrayList<News>
                    updateUI(list);
                }
            }
            .addOnFailureListener {
                Timber.w("Error getting documents.")
            }
    }

    private fun updateUI(list: ArrayList<News>) {
        val coursesAdapter = NewsAdapter(list)
        newsRecyclerView.adapter = coursesAdapter
    }
}