/*
package com.iuturakulov.hseapple.model.api;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Timestamp;

import javax.persistence.*;

@Entity
@Table(name = "task", schema = "public")
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

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "form")
    private String form;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "courseid")
    private Long courseID;

    @Column(name = "createdby")
    private Long createdBy;

    @Column(name = "updatedby")
    private Long updatedBy;

    @Column(name = "task_content")
    private String task_content;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSS")
    @Column(name = "deadline")
    private Timestamp deadline;

    public boolean isStatus() {
        return status;
    }

    @Column(name = "status")
    private boolean status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSS")
    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;



    //getters and setters
}
*/
