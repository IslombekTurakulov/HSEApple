package com.iuturakulov.hseapple.model.models;

import androidx.room.Entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.iuturakulov.hseapple.utils.CurrentCourse;
import com.iuturakulov.hseapple.utils.RoleOfUsers;

@Entity(tableName = "user_table")
public class Person {
    @SerializedName("full_name")
    @Expose
    private final String fullName;
    @SerializedName("email_person")
    @Expose
    private final String emailPerson;
    @SerializedName("current_course")
    @Expose
    private CurrentCourse currentCourse;
    @SerializedName("role_user")
    @Expose
    private RoleOfUsers roleOfUsers;

    public Person(String fullName, String email, CurrentCourse currentCourse, RoleOfUsers role) {
        super();
        this.fullName = fullName;
        this.emailPerson = email;
        this.currentCourse = currentCourse;
        roleOfUsers = role;
    }

    public String getEmailPerson() {
        return emailPerson;
    }

    public String getFullName() {
        return fullName;
    }

    public CurrentCourse getCurrentCourse() {
        return currentCourse;
    }

    public void setCurrentCourse(CurrentCourse currentCourse) {
        this.currentCourse = currentCourse;
    }

    public RoleOfUsers getRoleOfUsers() {
        return roleOfUsers;
    }

    public void setRoleOfUsers(RoleOfUsers roleOfUsers) {
        this.roleOfUsers = roleOfUsers;
    }
}
