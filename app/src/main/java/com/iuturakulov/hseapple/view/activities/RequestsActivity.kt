package com.iuturakulov.hseapple.view.activities

import android.graphics.Canvas
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.iuturakulov.hseapple.R
import com.iuturakulov.hseapple.model.api.RequestEntity
import com.iuturakulov.hseapple.utils.TOKEN_API
import com.iuturakulov.hseapple.view.adapters.RequestsAdapter
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator
import kotlinx.android.synthetic.main.activity_requests.*
import okhttp3.*
import timber.log.Timber
import java.io.IOException

class RequestsActivity : AppCompatActivity() {

    private var requestsAdapter: RequestsAdapter? = null

    val tempApi =
        "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsIng1dCI6Il9KLTZ5TzRqSTMzZk9IOUxsYjdyRzJoTXVGOCIsImtpZCI6Il9KLTZ5TzRqSTMzZk9IOUxsYjdyRzJoTXVGOCJ9.eyJhdWQiOiJtaWNyb3NvZnQ6aWRlbnRpdHlzZXJ2ZXI6MGQ0NmFlMDUtM2JiYy00MzQ2LWEzYTMtMWQ3MzJiNDllYTUzIiwiaXNzIjoiaHR0cDovL2F1dGguaHNlLnJ1L2FkZnMvc2VydmljZXMvdHJ1c3QiLCJpYXQiOjE2NTExMzgyNzIsIm5iZiI6MTY1MTEzODI3MiwiZXhwIjoxNjUxMTQxODcyLCJnaXZlbl9uYW1lIjoi0JjRgdC70L7QvNCx0LXQuiIsImNvbW1vbm5hbWUiOiLQotGD0YDQsNC60YPQu9C-0LIg0JjRgdC70L7QvNCx0LXQuiDQo9C70YPQs9Cx0LXQutC-0LLQuNGHIiwiZmFtaWx5X25hbWUiOiLQotGD0YDQsNC60YPQu9C-0LIiLCJlbWFpbCI6Iml1dHVyYWt1bG92QGVkdS5oc2UucnUiLCJhcHB0eXBlIjoiUHVibGljIiwiYXBwaWQiOiIwZDQ2YWUwNS0zYmJjLTQzNDYtYTNhMy0xZDczMmI0OWVhNTMiLCJhdXRobWV0aG9kIjoidXJuOm9hc2lzOm5hbWVzOnRjOlNBTUw6Mi4wOmFjOmNsYXNzZXM6UGFzc3dvcmRQcm90ZWN0ZWRUcmFuc3BvcnQiLCJhdXRoX3RpbWUiOiIyMDIyLTA0LTI3VDE1OjU3OjI2LjU5M1oiLCJ2ZXIiOiIxLjAifQ.FeYIZyEk7K1IfFUBONDFz7jwIgXraGFhuAhEVhWriya4rnYklfG2bE_QoFaYyOtZ2AHu9kCrdcGqo9CHgGSGpnk78X_dA0MCnVFouvo8ed1WlTxVO8bqWrfCdYILEbjRkU1hOsMw-htRwI41rREIGDHpQYFj9fCJ2ctxvR-gQsdd4izLltkDueZwl-cckw6iCEnW-_QmsNb4IrpX5d5nHBvZ21OgzSl7W8ESEBKovfFgtols4D6a9JqQWZdgMaUdq1MO8kGwRuKlwGRJe3-cDvRJ4kvwwyfdURoD7ppl7KvftnMWpnGSQEgaTdDDTKmp-DAC-bRvmW5V76xxpC886g"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_requests)
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.addItemDecoration(
            DividerItemDecoration(
                this,
                LinearLayoutManager.VERTICAL
            )
        )
        requestsAdapter = RequestsAdapter(this)
        recycler_view.adapter = requestsAdapter
        val callback: ItemTouchHelper.SimpleCallback = initializeCallBacks()
        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(recycler_view)
    }

    private fun initializeCallBacks(): ItemTouchHelper.SimpleCallback {
        val callback: ItemTouchHelper.SimpleCallback = object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                try {
                    val position = viewHolder.adapterPosition
                    val item: RequestEntity? = requestsAdapter!!.removeItem(position)
                    val snack = Snackbar.make(
                        viewHolder.itemView,
                        "Item " + (if (direction == ItemTouchHelper.RIGHT) "denied" else "accepted") + ".",
                        Snackbar.LENGTH_LONG
                    )
                    snack.setAction(android.R.string.cancel) {
                        try {
                            requestsAdapter!!.addItem(item!!, position)
                        } catch (e: Exception) {
                            Timber.e(e.message!!)
                        }
                    }
                    snack.show()
                } catch (e: Exception) {
                    Timber.e(e.message!!)
                }
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
                    .addSwipeLeftBackgroundColor(
                        ContextCompat.getColor(
                            this@RequestsActivity,
                            R.color.recycler_view_item_swipe_left_background
                        )
                    )
                    .addSwipeLeftActionIcon(R.drawable.ic_person_add_24)
                    .addSwipeRightBackgroundColor(
                        ContextCompat.getColor(
                            this@RequestsActivity,
                            R.color.recycler_view_item_swipe_right_background
                        )
                    )
                    .addSwipeRightActionIcon(R.drawable.ic_delete_white_24dp)
                    .addSwipeRightLabel("User deleted")
                    .setSwipeRightLabelColor(Color.WHITE)
                    .addSwipeLeftLabel("User accepted")
                    .setSwipeLeftLabelColor(Color.WHITE) //.addCornerRadius(TypedValue.COMPLEX_UNIT_DIP, 16)
                    //.addPadding(TypedValue.COMPLEX_UNIT_DIP, 8, 16, 8)
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
        val items = requestsAdapter!!.getAllItems()
        val client = OkHttpClient()
        val mediaType = MediaType.parse("application/json")
        val body = RequestBody.create(mediaType, "{\n  \"approved\": true\n}")
        for (item in items) {
            val request = Request.Builder()
                .url("80.66.64.53:8080/request/${item.courseID}/${item.userID}")
                .put(body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Prefer", "code=200")
                .addHeader("token", TOKEN_API)
                .addHeader("Authorization", tempApi)
                .build()
            var response: Response? = null
            try {
                response = client.newCall(request).execute()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            if (response != null) {
                println(response.body().toString())
            }
        }
    }
}