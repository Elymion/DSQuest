package com.dreamstory.quest

import com.dreamstory.quest.coroutine.schedule
import com.mojang.datafixers.util.Pair
import net.minecraft.network.protocol.game.PacketPlayOutEntityEquipment
import net.minecraft.world.entity.EnumItemSlot
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.craftbukkit.v1_18_R2.entity.CraftPlayer
import org.bukkit.craftbukkit.v1_18_R2.inventory.CraftItemStack
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import java.util.*

object Dialog {
    private val inDialog = HashMap<UUID,Boolean>()

    fun onDialog(player: Player) {
        inDialog[player.uniqueId] = true
    }
    fun offDialog(player: Player) {
        inDialog[player.uniqueId] = false
    }
    fun hasDialog(player: Player) = inDialog[player.uniqueId] ?: false

    fun onTitleCutscene(player: Player, list: List<String>, interval: Int, sound: Sound) {
        if (interval < 40) return
        main.schedule {
            list.forEach {
                if (!player.isOnline) {
                    inDialog[player.uniqueId] = false
                    return@schedule
                }
                player.sendTitle(" ",it,20,interval-40,20)
                player.playSound(player.location,sound,1f,1f)
                waitFor(interval.toLong())
            }
        }
    }

    fun lockingView(player: Player) {
        val loc = player.location
        main.schedule {
            while (player.isOnline && hasDialog(player)) {
                if (player.location != loc) player.teleport(loc)
                waitFor(1)
            }
            inDialog[player.uniqueId] = false
        }
    }

    fun pumpkinView(player: Player) {
        val packet = PacketPlayOutEntityEquipment(player.entityId, listOf(
            Pair(
                EnumItemSlot.f,
                CraftItemStack.asNMSCopy(ItemStack(Material.CARVED_PUMPKIN, 1)))
        ))
        val craftPlayer = (player as CraftPlayer).handle
        craftPlayer.b.a(packet)
    }

}