package com.github.highright1234.chocor.bungee

import com.google.common.io.ByteStreams
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin

class ChocorBungee

fun Player.connect(serverName : String, plugin : JavaPlugin) {
    @Suppress("UnstableApiUsage")
    val out = ByteStreams.newDataOutput()
    out.writeUTF("Connect")
    out.writeUTF(serverName)
    sendPluginMessage(plugin, "BungeeCord", out.toByteArray())
}

fun JavaPlugin.connect(player: Player, serverName: String) = player.connect(serverName, this)



