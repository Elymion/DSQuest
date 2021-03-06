package com.dreamstory.quest

import io.lumine.mythic.bukkit.events.MythicMobInteractEvent
import net.citizensnpcs.api.event.NPCClickEvent
import org.betonquest.betonquest.api.PlayerConversationEndEvent
import org.betonquest.betonquest.api.PlayerConversationStartEvent
import org.betonquest.betonquest.conversation.Conversation
import org.betonquest.betonquest.utils.PlayerConverter
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerCommandPreprocessEvent
import org.bukkit.event.player.PlayerJoinEvent

object Listener:Listener {

    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        val player = event.player
        if (!player.isOp) tutorial(player)
    }

    @EventHandler
    fun onConversationStart(event: PlayerConversationStartEvent) {
        val player = event.player
        Dialog.onDialog(player)
        //Dialog.pumpkinView(player)
    }

    @EventHandler
    fun onConversationEnd(event: PlayerConversationEndEvent) {
        val player = event.player
        Dialog.offDialog(player)
        player.updateInventory()
    }

    @EventHandler
    fun onCommandPreprocess(event: PlayerCommandPreprocessEvent) {
        val player = event.player
        if (Dialog.hasDialog(player) && !player.isOp) {
            player.sendMessage("§c! ! §e 대화 도중 명령어 입력 불가!")
            event.isCancelled = true
        }
    }

    @EventHandler
    fun onClickNPC(event: NPCClickEvent) {
        val player = event.clicker
        if (!player.isOnGround) return
        event.npc.name
    }

    @EventHandler
    fun onInteractMM(event: MythicMobInteractEvent){
        val player = event.player
        if (!player.isOnGround) return
        main.server.broadcastMessage("name: ${event.activeMob.displayName} type: ${event.activeMob.mobType}")
        val playerID = PlayerConverter.getID(player)
        Conversation(playerID,mythicMobMap[event.activeMob.mobType]?: return,player.location)
    }
}