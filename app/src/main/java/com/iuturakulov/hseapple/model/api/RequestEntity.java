package com.iuturakulov.hseapple.model.api;

import java.sql.Timestamp;

public class RequestEntity {
    private Long id;

    public Long getUserID() {
        return userID;
    }

    public Long getCourseID() {
        return courseID;
    }

    public Long getRoleID() {
        return roleID;
    }

    public Boolean getApproved() {
        return approved;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }
    private Long userID;
    private Long courseID;
    private Long roleID;
    private Boolean approved;
    private Timestamp createdAt;

    private Timestamp updatedAt;

    public Long getId() {
        return id;
    }
}
