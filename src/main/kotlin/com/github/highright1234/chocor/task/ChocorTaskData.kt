package com.github.highright1234.chocor.task

import org.bukkit.scheduler.BukkitTask

class ChocorTaskData {
    lateinit var task : BukkitTask
    fun cancel() = task.cancel()
    val isSync = task.isSync
    var isCancelled = task.isCancelled
        set(value) = cancel()
    val taskId = task.taskId
    val owner = task.owner
}