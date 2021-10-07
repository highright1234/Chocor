package com.github.highright1234.chocor

import org.bukkit.Bukkit
import org.bukkit.event.Event
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.scheduler.BukkitTask
import kotlin.reflect.KClass

class Chocor

fun callEvent(event : Event) {
    Bukkit.getServer().pluginManager.callEvent(event)
}

fun <T : Event> listener(clazz : KClass<T>, plugin : JavaPlugin, listener: T.() -> Unit) : Listener {
    val listenerData = object : Listener {
        @EventHandler
        fun on(event : T) {
            listener(event)
        }
    }
    Bukkit.getServer().pluginManager.registerEvents(listenerData, plugin)
    return listenerData
}

fun onRightClick(plugin: JavaPlugin, listener: PlayerInteractEvent.() -> Unit) : Listener {
    return listener(PlayerInteractEvent::class, plugin) {
        if (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {
            listener(this)
        }
    }
}

fun onLeftClick(plugin: JavaPlugin, listener: PlayerInteractEvent.() -> Unit) : Listener {
    return listener(PlayerInteractEvent::class, plugin) {
        if (action == Action.LEFT_CLICK_AIR || action == Action.LEFT_CLICK_BLOCK) {
            listener(this)
        }
    }
}

fun pluginMessage(pluginMessageChannel : String, plugin : JavaPlugin, listener: PluginMessageData.() -> Unit) {
    Bukkit.getServer().messenger.registerIncomingPluginChannel( plugin, pluginMessageChannel) { channel, player, message ->
        if (pluginMessageChannel.equals(channel, true)) {
            listener(PluginMessageData(player, message))
        }
    }
}

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

fun countingLoopTask(
    times : Int = 0,
    delay : Long = 0,
    period : Long = 0,
    plugin : JavaPlugin,
    task : (count : Int) -> Unit
) : BukkitTask {
    return loopTask(delay, period, plugin) {
        for (i in 1..times) {
            task(i)
        }
    }
}

fun asyncCountingLoopTask(
    times : Int = 0,
    delay : Long = 0,
    period : Long = 0,
    plugin : JavaPlugin,
    task : (count : Int) -> Unit
) : BukkitTask {
    return asyncLoopTask(delay, period, plugin) {
        for (i in 1..times) {
            task(i)
        }
    }
}

fun countDownLoopTask(
    times : Int = 0,
    delay : Long = 0,
    period : Long = 0,
    plugin : JavaPlugin,
    task : (count : Int) -> Unit
) : BukkitTask {
    return loopTask(delay, period, plugin) {
        for (i in times..1) {
            task(i)
        }
    }
}

fun asyncCountDownLoopTask(
    times : Int = 0,
    delay : Long = 0,
    period : Long = 0,
    plugin : JavaPlugin,
    task : (count : Int) -> Unit
) : BukkitTask {
    return asyncLoopTask(delay, period, plugin) {
        for (i in times..1) {
            task(i)
        }
    }
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

@JvmName("onRightClick1")
fun JavaPlugin.onRightClick(listener: PlayerInteractEvent.() -> Unit) {
    onRightClick(this, listener)
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

fun JavaPlugin.countingLoopTask(
    times : Int = 0,
    delay : Long = 0,
    period : Long = 0,
    task : (count : Int) -> Unit
) : BukkitTask {
    return loopTask(delay, period) {
        for (i in 1..times) {
            task(i)
        }
    }
}

fun JavaPlugin.asyncCountingLoopTask(
    times : Int = 0,
    delay : Long = 0,
    period : Long = 0,
    task : (count : Int) -> Unit
) : BukkitTask {
    return asyncLoopTask(delay, period) {
        for (i in 1..times) {
            task(i)
        }
    }
}

fun JavaPlugin.countDownLoopTask(
    times : Int = 0,
    delay : Long = 0,
    period : Long = 0,
    task : (count : Int) -> Unit
) : BukkitTask {
    return loopTask(delay, period) {
        for (i in times..1) {
            task(i)
        }
    }
}

fun JavaPlugin.asyncCountDownLoopTask(
    times : Int = 0,
    delay : Long = 0,
    period : Long = 0,
    task : (count : Int) -> Unit
) : BukkitTask {
    return asyncLoopTask(delay, period) {
        for (i in times..1) {
            task(i)
        }
    }
}

fun JavaPlugin.runTaskLater(delay : Long, task : () -> Unit) : BukkitTask {
    return runTaskLater(delay, this, task)
}

fun JavaPlugin.runTaskLaterAsync(delay : Long, task : () -> Unit) : BukkitTask {
    return runTaskLaterAsync(delay, this, task)
}