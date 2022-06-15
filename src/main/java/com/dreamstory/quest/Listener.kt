package com.dreamstory.quest

import io.lumine.mythic.bukkit.events.MythicMobInteractEvent
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
    fun onInteractMM(event: MythicMobInteractEvent){
        main.server.broadcastMessage("name: ${event.activeMob.displayName} type: ${event.activeMob.mobType}")
        val playerID = PlayerConverter.getID(event.player)
        Conversation(playerID,conversationMap[event.activeMob.mobType]?: return,event.player.location)
    }
}