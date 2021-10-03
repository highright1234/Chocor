package com.github.highright1234.chocor

import org.bukkit.Bukkit
import org.bukkit.event.Event
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.scheduler.BukkitTask
import kotlin.reflect.KClass

class Chocor

fun callEvent(event : Event) {
    Bukkit.getServer().pluginManager.callEvent(event)
}

fun <T : Event> listener(clazz : KClass<T>, plugin : JavaPlugin, listener: T.() -> Unit) {
    Bukkit.getServer().pluginManager.registerEvents(object : Listener {
        @EventHandler
        fun on(event : T) {
            listener(event)
        }
    }, plugin)
}

fun pluginMessage(pluginMessageChannel : String, plugin : JavaPlugin, listener: PluginMessageData.() -> Unit) {
    Bukkit.getServer().messenger.registerIncomingPluginChannel( plugin, pluginMessageChannel) { channel, player, message ->
        if (pluginMessageChannel.equals(channel, true)) {
            listener(PluginMessageData(player, message))
        }
    };
}

fun loopTask(delay : Long = 0, period : Long = 0, plugin : JavaPlugin, task : () -> Unit) : BukkitTask{
    var runnable : BukkitRunnable? = null
    runnable = object : BukkitRunnable() {
        override fun run() {
            task()
        }
    }
    return runnable.runTaskTimer(plugin, delay, period)
}

fun asyncLoopTask(delay : Long = 0, period : Long = 0, plugin : JavaPlugin, task : () -> Unit) : BukkitTask {
    var runnable : BukkitRunnable? = null
    runnable = object : BukkitRunnable() {
        override fun run() {
            task()
        }
    }
    return runnable.runTaskTimerAsynchronously(plugin, delay, period)
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












fun <T : Event> JavaPlugin.listener(clazz : KClass<T>, listener : T.() -> Unit) {
    listener(clazz, this, listener)
}

fun JavaPlugin.pluginMessage(channel : String, listener: PluginMessageData.() -> Unit) {
    pluginMessage(channel, this, listener)
}

fun JavaPlugin.loopTask(delay : Long = 0, period : Long = 0, task : () -> Unit) : BukkitTask {
    return loopTask(delay, period, this, task)
}

fun JavaPlugin.asyncLoopTask(delay : Long = 0, period : Long = 0, task : () -> Unit) : BukkitTask {
    return asyncLoopTask(delay, period, this, task)
}

fun JavaPlugin.runTaskLater(delay : Long, task : () -> Unit) : BukkitTask {
    return runTaskLater(delay, this, task)
}

fun JavaPlugin.runTaskLaterAsync(delay : Long, task : () -> Unit) : BukkitTask {
    return runTaskLaterAsync(delay, this, task)
}