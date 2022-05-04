package com.iuturakulov.hseapple.view.activities

import android.graphics.Canvas
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cometchat.pro.models.User
import com.cometchat.pro.uikit.ui_components.users.user_list.CometChatUserList
import com.google.android.material.snackbar.Snackbar
import com.iuturakulov.hseapple.R
import com.iuturakulov.hseapple.view.adapters.AssistantsAdapter
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator
import kotlinx.android.synthetic.main.activity_list_of_assistants.*
import kotlinx.android.synthetic.main.activity_requests.*
import kotlinx.android.synthetic.main.activity_requests.recycler_view
import kotlinx.android.synthetic.main.toolbar_assistants.*
import kotlinx.android.synthetic.main.toolbar_requests.*
import okhttp3.*
import timber.log.Timber
import java.io.IOException

class ListOfAssistantsActivity : AppCompatActivity() {

    private var assistantAdapter: AssistantsAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_of_assistants)
        back_arrow_assistants.setOnClickListener {
            onBackPressed()
        }
        assistants_name.text = getString(R.string.assistants)
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.addItemDecoration(
            DividerItemDecoration(
                this,
                LinearLayoutManager.VERTICAL
            )
        )
        assistantAdapter = AssistantsAdapter(this)
        recycler_view.adapter = assistantAdapter
        if (assistantAdapter!!.getAllItems().isNotEmpty()) {
            isAssistantsEmptyImage.visibility = View.GONE
            isAssistantsEmptyText.visibility = View.GONE
        }
        val callback: ItemTouchHelper.SimpleCallback = initializeCallBacks()
        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(recycler_view)
    }
    private fun initializeCallBacks(): ItemTouchHelper.SimpleCallback {
        val callback: ItemTouchHelper.SimpleCallback = object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) = try {
                val position = viewHolder.adapterPosition
                val item: User? = assistantAdapter!!.removeItem(position)
                if (assistantAdapter!!.getAllItems().isNotEmpty()) {
                    isAssistantsEmptyImage.visibility = View.GONE
                    isAssistantsEmptyText.visibility = View.GONE
                }
                val snack = Snackbar.make(
                    viewHolder.itemView,
                    "Item ${if (direction == ItemTouchHelper.RIGHT) "denied" else "accepted"}.",
                    Snackbar.LENGTH_LONG
                )
                snack.setAction(android.R.string.cancel) {
                    try {
                        assistantAdapter!!.addItem(item!!, position)
                        if (assistantAdapter!!.getAllItems().isNotEmpty()) {
                            isAssistantsEmptyImage.visibility = View.GONE
                            isAssistantsEmptyText.visibility = View.GONE
                        }
                    } catch (e: Exception) {
                        Timber.e(e.message!!)
                    }
                }
                snack.show()
            } catch (e: Exception) {
                Timber.e(e.message!!)
            }

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                RecyclerViewSwipeDecorator.Builder(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
                    .addSwipeRightBackgroundColor(
                        ContextCompat.getColor(
                            this@ListOfAssistantsActivity,
                            R.color.recycler_view_item_swipe_right_background
                        )
                    )
                    .addSwipeRightActionIcon(R.drawable.ic_delete_white_24dp)
                    .addSwipeRightLabel("User deleted")
                    .setSwipeRightLabelColor(Color.WHITE)
                    .create()
                    .decorate()
                super.onChildDraw(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
            }
        }
        return callback
    }

    override fun onDestroy() {
        super.onDestroy()
        val items = assistantAdapter!!.getAllItems()
        val client = OkHttpClient()
        val mediaType = MediaType.parse("application/json")
        for (item in items) {
            val body = RequestBody.create(
                mediaType,
                "{\"name\":\"${item.name}\",\"avatar\":\"${item.avatar}\",\"link\":\"${item.link}\",\"role\":\"default\",\"metadata\":${item.metadata},\"tags\":${item.tags},\"unset\":${item.tags}}"
            )
            val request = Request.Builder()
                .url("https://2080788f45f25844.api-eu.cometchat.io/v3/users/iturakulov")
                .put(body)
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
                .build()
            var response: Response? = null
            try {
                response = client.newCall(request).execute()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            if (response != null) {
                Timber.w(if (response.isSuccessful) "${item.name} deleted" else "${item.name} with failure")
            }
        }
    }

    private fun loadFragment(fragment: Fragment?): Boolean {
        if (fragment != null) {
            supportFragmentManager.beginTransaction().replace(R.id.frameOfUsers, fragment).commit()
            return true
        }
        return false
    }
}