package com.dreamstory.quest.events

import net.citizensnpcs.api.CitizensAPI
import org.betonquest.betonquest.Instruction
import org.betonquest.betonquest.api.QuestEvent
import org.bukkit.event.player.PlayerTeleportEvent

class TeleportNpcEvent(instruction: Instruction): QuestEvent(instruction,true) {
    private val id = instruction.int
    private val loc = instruction.location
    override fun execute(playerID: String?): Void? {
        CitizensAPI.getNPCRegistry().getById(id).teleport(loc.getLocation(playerID),PlayerTeleportEvent.TeleportCause.PLUGIN)
        return null
    }
}