package com.github.highright1234.chocor

import org.bukkit.event.player.PlayerTeleportEvent
import org.bukkit.plugin.java.JavaPlugin

class ChocorPlugin : JavaPlugin() {
    override fun onEnable() {
        listener(PlayerTeleportEvent::class) {

        }
    }
}