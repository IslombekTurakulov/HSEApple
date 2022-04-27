package com.iuturakulov.hseapple.model.listeners

import com.iuturakulov.hseapple.model.models.Person

interface UserListener {
    fun onUserClicked(user: Person)
}