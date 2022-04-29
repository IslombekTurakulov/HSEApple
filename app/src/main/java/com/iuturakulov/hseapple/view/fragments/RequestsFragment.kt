package com.iuturakulov.hseapple.view.fragments

import android.graphics.Canvas
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
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
import kotlinx.android.synthetic.main.fragment_requests.*
import okhttp3.*
import java.io.IOException

class RequestsFragment : Fragment(R.layout.fragment_requests) {

    private var mAdapter: RequestsAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        request_swipe_refresh.setOnRefreshListener {
            mAdapter = RequestsAdapter(requireContext())
            recycler_view.adapter = mAdapter
            request_swipe_refresh.isRefreshing = false
        }
        recycler_view.layoutManager = LinearLayoutManager(requireContext())
        recycler_view.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                LinearLayoutManager.VERTICAL
            )
        )
        mAdapter = RequestsAdapter(requireContext())
        recycler_view.adapter = mAdapter
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
                    val item: RequestEntity? = mAdapter!!.removeItem(position)
                    val snack = Snackbar.make(
                        viewHolder.itemView,
                        "Item " + (if (direction == ItemTouchHelper.RIGHT) "denied" else "accepted") + ".",
                        Snackbar.LENGTH_LONG
                    )
                    snack.setAction(android.R.string.cancel) {
                        try {
                            mAdapter!!.addItem(item!!, position)
                        } catch (e: Exception) {
                            Log.e("RequestFragment", e.message!!)
                        }
                    }
                    snack.show()
                } catch (e: Exception) {
                    Log.e("RequestFragment", e.message!!)
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
                            requireContext(),
                            R.color.recycler_view_item_swipe_left_background
                        )
                    )
                    .addSwipeLeftActionIcon(R.drawable.ic_person_add_24)
                    .addSwipeRightBackgroundColor(
                        ContextCompat.getColor(
                            requireContext(),
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
        val items = mAdapter!!.getAllItems()
        val client = OkHttpClient()
        val mediaType = MediaType.parse("application/json")
        val body = RequestBody.create(mediaType, "{\n  \"approved\": true\n}")
        for (item in items) {
            val request = Request.Builder()
                .url("https://stoplight.io/mocks/hseapple/nis-app/38273133/request/${item.courseID}/${item.userID}")
                .put(body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Prefer", "code=200")
                .addHeader("token", TOKEN_API)
                .build()
            var response: Response? = null
            try {
                response = client.newCall(request).execute()
            } catch (e: IOException) {
                e.printStackTrace();
            }
            if (response != null) {
                println(response.body().toString())
            }
        }
    }
}