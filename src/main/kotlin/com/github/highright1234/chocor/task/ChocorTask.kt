package com.github.highright1234.chocor.task

import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.scheduler.BukkitTask

object ChocorTask

fun loopTask(delay : Long = 0, period : Long = 0, plugin : JavaPlugin, task : ChocorTaskData.() -> Unit) : BukkitTask {
    val chocorTaskData = ChocorTaskData()
    lateinit var runnable : BukkitRunnable
    runnable = object : BukkitRunnable() {
        override fun run() {
            task(chocorTaskData)
        }
    }
    return runnable.runTaskTimer(plugin, delay, period).also { chocorTaskData.task = it }
}

fun asyncLoopTask(delay : Long = 0, period : Long = 0, plugin : JavaPlugin, task : ChocorTaskData.() -> Unit) : BukkitTask {
    val chocorTaskData = ChocorTaskData()
    chocorTaskData.task = object : BukkitRunnable() {
        override fun run() {
            chocorTaskData.task()
        }
    }.runTaskTimerAsynchronously(plugin, delay, period)
    return chocorTaskData.task
}

fun <T> loopTask(
    delay : Long = 0,
    period : Long = 0,
    iterator: Iterator<T>,
    plugin : JavaPlugin,
    task : ChocorTaskData.(T) -> Unit
) : BukkitTask {
    return loopTask(delay, period, plugin) { if (iterator.hasNext()) task(iterator.next()) else cancel() }
}

fun <T> loopTask(
    iterable: Iterable<T>,
    delay : Long = 0,
    period : Long = 0,
    plugin : JavaPlugin,
    task : ChocorTaskData.(T) -> Unit
) = loopTask(delay, period, iterable.iterator(), plugin, task)

fun <T> loopTask(
    delay : Long = 0,
    period : Long = 0,
    collection: Collection<T>,
    plugin : JavaPlugin,
    task : ChocorTaskData.(T) -> Unit
) = loopTask(delay, period, collection.iterator(), plugin, task)

fun <T> asyncLoopTask(
    delay : Long = 0,
    period : Long = 0,
    collection: Collection<T>,
    plugin : JavaPlugin,
    task : ChocorTaskData.(T) -> Unit
) : BukkitTask = asyncLoopTask(delay, period, collection.iterator(), plugin, task)

fun <T> asyncLoopTask(
    delay : Long = 0,
    period : Long = 0,
    iterator: Iterator<T>,
    plugin : JavaPlugin,
    task : ChocorTaskData.(T) -> Unit
) : BukkitTask = asyncLoopTask(delay, period, plugin) { if (iterator.hasNext()) task(iterator.next()) else cancel() }

fun <T> asyncLoopTask(
    iterable : Iterable<T>,
    delay : Long = 0,
    period : Long = 0,
    plugin : JavaPlugin,
    task : ChocorTaskData.(T) -> Unit
) : BukkitTask = asyncLoopTask(delay, period, iterable.iterator(), plugin, task)

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
    delay : Long = 0, period : Long = 0, task : ChocorTaskData.() -> Unit
) : BukkitTask = loopTask(delay, period, this, task)

fun <T> JavaPlugin.loopTask(
    delay : Long = 0, period : Long = 0, collection: Collection<T>,task : ChocorTaskData.(T) -> Unit
) : BukkitTask = loopTask(delay, period, collection, this, task)

fun <T> JavaPlugin.loopTask(
    delay : Long = 0, period : Long = 0, iterator: Iterator<T>,task : ChocorTaskData.(T) -> Unit
) : BukkitTask = loopTask(delay, period, iterator, this, task)

fun JavaPlugin.asyncLoopTask(
    delay : Long = 0, period : Long = 0, task : ChocorTaskData.() -> Unit
) : BukkitTask = asyncLoopTask(delay, period, this,task)

fun <T> JavaPlugin.loopTask(
    iterable : Iterable<T>,
    delay : Long = 0,
    period : Long = 0,
    task : ChocorTaskData.(T) -> Unit
) : BukkitTask = loopTask(iterable, delay, period, this, task)

fun <T> JavaPlugin.asyncLoopTask(
    iterable : Iterable<T>,
    delay : Long = 0,
    period : Long = 0,
    task : ChocorTaskData.(T) -> Unit
) : BukkitTask = asyncLoopTask(delay, period, iterable.iterator(), this, task)

fun JavaPlugin.runTaskLater(
    delay : Long, task : () -> Unit
) : BukkitTask = runTaskLater(delay, this, task)

fun JavaPlugin.runTaskLaterAsync(
    delay : Long, task : () -> Unit
) : BukkitTask = runTaskLater(delay, this, task)
