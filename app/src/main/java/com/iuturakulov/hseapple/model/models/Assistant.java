package com.iuturakulov.hseapple.model.models;

import androidx.room.Entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.iuturakulov.hseapple.utils.CurrentCourse;
import com.iuturakulov.hseapple.utils.RoleOfUsers;

@Entity(tableName = "assistant_table")
public class Assistant extends Person {
    @SerializedName("unique_id")
    @Expose
    private final int uniqueId;

    public Assistant(String fullName, String email, CurrentCourse currentCourse, RoleOfUsers role, int uniqueId) {
        super(fullName, email, currentCourse, role);
        this.uniqueId = uniqueId;
    }

    public int getUniqueId() {
        return uniqueId;
    }
}
