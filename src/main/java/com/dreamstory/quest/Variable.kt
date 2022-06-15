package com.dreamstory.quest

import com.comphenix.protocol.PacketType
import com.dreamstory.library.protocolManager
import net.minecraft.network.protocol.game.PacketPlayOutEntityHeadRotation
import org.betonquest.betonquest.BetonQuest
import org.betonquest.betonquest.id.EventID
import org.betonquest.betonquest.utils.PlayerConverter
import org.bukkit.Location
import org.bukkit.craftbukkit.v1_18_R2.entity.CraftEntity
import org.bukkit.craftbukkit.v1_18_R2.entity.CraftPlayer
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin

internal lateinit var main: JavaPlugin
internal lateinit var betonQuest: BetonQuest
val conversationMap = hashMapOf(
    "blacksmithNPCHITBOX" to "Tutorial.monolog",
    "cauldron" to "Crafting.cooking"
)
fun tutorial(player: Player) {
    player.teleport(Location(main.server.worlds.first(),-63.5,54.0,-601.5,13f,13f))
    BetonQuest.event(PlayerConverter.getID(player), EventID(null, "Tutorial.folder_start"))
}
fun headRotation(player: Player) {
    val p = protocolManager.createPacket(PacketType.Play.Server.ENTITY_HEAD_ROTATION)
    val packet = PacketPlayOutEntityHeadRotation((player as CraftEntity).handle, (11).toByte())
    (player as CraftPlayer).handle.b.a(packet)
}