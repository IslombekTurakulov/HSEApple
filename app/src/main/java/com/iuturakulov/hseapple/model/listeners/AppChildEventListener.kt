package com.iuturakulov.hseapple.model.listeners

import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError

class AppChildEventListener (val onSuccess:(DataSnapshot) -> Unit) : ChildEventListener {
    override fun onCancelled(p0: DatabaseError) {
    }

    override fun onChildMoved(p0: DataSnapshot, p1: String?) {
    }

    override fun onChildChanged(p0: DataSnapshot, p1: String?) {
    }

    override fun onChildAdded(p0: DataSnapshot, p1: String?) {
        onSuccess(p0)
    }

    override fun onChildRemoved(p0: DataSnapshot) {
    }
}