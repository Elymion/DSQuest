package com.dreamstory.quest.conditions

import org.betonquest.betonquest.Instruction
import org.betonquest.betonquest.api.Condition
import org.betonquest.betonquest.utils.PlayerConverter

class IsOnGround(ins:Instruction): Condition(ins,false) {
    override fun execute(playerID: String?): Boolean {
        val player = PlayerConverter.getPlayer(playerID)
        return player.isOnGround
    }
}