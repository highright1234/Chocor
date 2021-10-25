package com.github.highright1234.chocor.task

import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.scheduler.BukkitTask

object ChocorTask

fun loopTask(delay : Long = 0, period : Long = 0, plugin : JavaPlugin, task : () -> Unit) : BukkitTask {
    lateinit var runnable : BukkitRunnable
    runnable = object : BukkitRunnable() {
        override fun run() {
            task()
        }
    }
    return runnable.runTaskTimer(plugin, delay, period)
}

fun asyncLoopTask(delay : Long = 0, period : Long = 0, plugin : JavaPlugin, task : () -> Unit) : BukkitTask {
    lateinit var runnable : BukkitRunnable
    runnable = object : BukkitRunnable() {
        override fun run() {
            task()
        }
    }
    return runnable.runTaskTimerAsynchronously(plugin, delay, period)
}

fun loopTask(
    times : IntRange,
    delay : Long = 0,
    period : Long = 0,
    plugin : JavaPlugin,
    task : (count : Int) -> Unit
) : BukkitTask {

    lateinit var taskData : BukkitTask
    var i = times.first

    taskData = loopTask(delay, period, plugin) {
        task(i)
        if (i < times.last) {
            i++
        } else if (i > times.last) {
            i--
        } else {
            taskData.cancel()
        }
    }
    return taskData
}

fun asyncLoopTask(
    times : IntRange,
    delay : Long = 0,
    period : Long = 0,
    plugin : JavaPlugin,
    task : (count : Int) -> Unit
) : BukkitTask {

    lateinit var taskData : BukkitTask
    var i = times.first

    taskData = asyncLoopTask(delay, period, plugin) {
        task(i)
        if (i < times.last) {
            i++
        } else if (i > times.last) {
            i--
        } else {
            taskData.cancel()
        }
    }

    return taskData

}

fun runTaskLater(delay : Long, plugin : JavaPlugin, task : () -> Unit) : BukkitTask {
    return object : BukkitRunnable() {
        override fun run() {
            task()
        }
    }.runTaskLater(plugin, delay)
}

fun runTaskLaterAsync(delay : Long, plugin : JavaPlugin, task : () -> Unit) : BukkitTask {
    return object : BukkitRunnable() {
        override fun run() {
            task()
        }
    }.runTaskLaterAsynchronously(plugin, delay)
}


fun JavaPlugin.loopTask(
    delay : Long = 0, period : Long = 0, task : () -> Unit
) : BukkitTask = loopTask(delay, period, this, task)

fun JavaPlugin.asyncLoopTask(
    delay : Long = 0, period : Long = 0, task : () -> Unit
) : BukkitTask = asyncLoopTask(delay, period, this, task)

fun JavaPlugin.loopTask(
    times : IntRange,
    delay : Long = 0,
    period : Long = 0,
    task : (count : Int) -> Unit
) : BukkitTask = loopTask(times, delay, period, this, task)

fun JavaPlugin.asyncLoopTask(
    times : IntRange,
    delay : Long = 0,
    period : Long = 0,
    task : (count : Int) -> Unit
) : BukkitTask = asyncLoopTask(times, delay, period, this, task)

fun JavaPlugin.runTaskLater(
    delay : Long, task : () -> Unit
) : BukkitTask = runTaskLater(delay, this, task)

fun JavaPlugin.runTaskLaterAsync(
    delay : Long, task : () -> Unit
) : BukkitTask = runTaskLater(delay, this, task)
