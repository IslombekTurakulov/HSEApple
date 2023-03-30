package com.iuturakulov.data.datasource.task

import com.iuturakulov.data.dao.TaskDao
import com.iuturakulov.domain.entities.TaskEntity

interface TaskLocalDataSource {
    suspend fun getTask(taskId: String): TaskEntity?
    suspend fun getTasks(): List<TaskEntity>
    suspend fun saveTask(task: TaskEntity)
    suspend fun saveTasks(tasks: List<TaskEntity>)
    suspend fun deleteTask(task: TaskEntity)
    suspend fun deleteTasks()
}

class TaskLocalDataSourceImpl(private val taskDao: TaskDao) : TaskLocalDataSource {
    override suspend fun getTask(taskId: String): TaskEntity? {
        return taskDao.getTask(taskId)
    }

    override suspend fun getTasks(): List<TaskEntity> {
        return taskDao.getTasks()
    }

    override suspend fun saveTask(task: TaskEntity) {
        taskDao.saveTask(task)
    }

    override suspend fun saveTasks(tasks: List<TaskEntity>) {
        taskDao.saveTasks(tasks)
    }

    override suspend fun deleteTask(task: TaskEntity) {
        taskDao.deleteTask(task)
    }

    override suspend fun deleteTasks() {
        taskDao.deleteAllTasks()
    }
}