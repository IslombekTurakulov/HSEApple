/*
package com.iuturakulov.hseapple.model.api;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Timestamp;

import javax.persistence.*;

@Entity
@Table(name = "user_task", schema = "public")
public class UserTaskEntity {
    public Long getId() {
        return id;
    }

    public String getAnswer() {
        return answer;
    }

    public Integer getScore() {
        return score;
    }

    public Boolean getStatus() {
        return status;
    }

    public Long getTaskID() {
        return taskID;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public Long getUpdatedBy() {
        return updatedBy;
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

    @Column(name = "answer")
    private String answer;

    @Column(name = "score")
    private Integer score;

    @Column(name = "status")
    private Boolean status;

    @Column(name = "taskid")
    private Long taskID;

    @Column(name = "createdby")
    private Long createdBy;

    @Column(name = "updatedby")
    private Long updatedBy;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSS")
    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;
}
*/
