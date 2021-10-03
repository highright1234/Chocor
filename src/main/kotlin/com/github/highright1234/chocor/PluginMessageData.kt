package com.github.highright1234.chocor

import org.bukkit.entity.Player

data class PluginMessageData(
    val player : Player,
    val data : ByteArray
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PluginMessageData

        if (player != other.player) return false
        if (!data.contentEquals(other.data)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = player.hashCode()
        result = 31 * result + data.contentHashCode()
        return result
    }
}
