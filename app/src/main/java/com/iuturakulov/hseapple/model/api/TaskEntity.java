
package com.iuturakulov.hseapple.model.api;

import java.security.Timestamp;

public class TaskEntity {
    public Long getId() {
        return id;
    }

    public String getForm() {
        return form;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Long getCourseID() {
        return courseID;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public String getTask_content() {
        return task_content;
    }

    public Timestamp getDeadline() {
        return deadline;
    }

/*
    // public Timestamp getCreatedAt() {
        return createdAt;
    }
*/

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }


    private Long id;

    private String form;

    private String title;

    private String description;

    private Long courseID;

    private Long createdBy;

    private Long updatedBy;

    private String task_content;

    private Timestamp deadline;

    public boolean isStatus() {
        return status;
    }
    private boolean status;

    private Timestamp updatedAt;



    //getters and setters
}
