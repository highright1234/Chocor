package com.github.highright1234.chocor.event

import com.github.highright1234.chocor.bungee.PluginMessageData
import org.bukkit.Bukkit
import org.bukkit.event.*
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.plugin.EventExecutor
import org.bukkit.plugin.IllegalPluginAccessException
import org.bukkit.plugin.RegisteredListener
import org.bukkit.plugin.TimedRegisteredListener
import org.bukkit.plugin.java.JavaPlugin
import java.lang.reflect.InvocationTargetException
import kotlin.reflect.KClass

class Chocor

fun callEvent(event : Event) {
    Bukkit.getServer().pluginManager.callEvent(event)
}

fun <T : Event> listener(
    clazz : KClass<T>,
    plugin : JavaPlugin,
    listener: T.() -> Unit
) : Listener {
    return listener(clazz, EventPriority.NORMAL, false, plugin, listener)
}

fun <T : Event> listener(
    clazz : KClass<T>,
    priority : EventPriority = EventPriority.NORMAL,
    ignoreCancelled : Boolean = false,
    plugin : JavaPlugin,
    listener: T.() -> Unit
) : Listener {
    val listenerData = object : Listener {
        @EventHandler
        fun on(event : T) {
            listener(event)
        }
    }
    val executor = EventExecutor { l, event ->
        runCatching {
            if (!clazz.java.isAssignableFrom(event.javaClass)) {
                return@EventExecutor
            }
            listenerData.javaClass.declaredMethods[0].invoke(l, event)
        }.exceptionOrNull()?.let {
            if (it is InvocationTargetException) throw EventException(it.cause)
            throw EventException(it)
        }
    }
    if (Bukkit.getServer().pluginManager.useTimings()) {
        clazz.java.handlerList.register(
            TimedRegisteredListener(
                listenerData,
                executor,
                priority,
                plugin,
                ignoreCancelled
            )
        )
    } else {
        clazz.java.handlerList.register(
            RegisteredListener(listenerData, executor, priority, plugin, ignoreCancelled)
        )
    }
    return listenerData
}

private val Class<out Event>.handlerList : HandlerList
    get() {
        val throwException = fun (): Nothing = throw IllegalPluginAccessException(
            "Unable to find handler list for event ${name}. Static getHandlerList method required!"
        )

        val exceptionableHandlerList = fun Class<out Event>.() : HandlerList {
            val method = getDeclaredMethod("getHandlerList").apply { isAccessible = true }
            return method.invoke(null) as HandlerList
        }
        var nowClass : Class<out Event> = this
        kotlin.runCatching { return nowClass.exceptionableHandlerList() }
        while (true) {
            kotlin.runCatching { return nowClass.exceptionableHandlerList() }
            nowClass.superclass ?: throwException()
            if (nowClass.superclass == Event::class.java) throwException()
            nowClass = nowClass.superclass as Class<out Event>
        }
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