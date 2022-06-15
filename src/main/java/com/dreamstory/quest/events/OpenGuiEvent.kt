package com.dreamstory.quest.events

import com.dreamstory.crafting.global.CraftType
import com.dreamstory.crafting.global.gui.CraftGUIManager
import org.betonquest.betonquest.Instruction
import org.betonquest.betonquest.api.QuestEvent
import org.betonquest.betonquest.exceptions.InstructionParseException
import org.betonquest.betonquest.utils.PlayerConverter

class OpenGuiEvent(instruction: Instruction): QuestEvent(instruction,true) {
    private val guiKey = instruction.next()
    override fun execute(playerID: String?): Void? {
        val player = PlayerConverter.getPlayer(playerID)
        when(guiKey) {
            "cooking" -> CraftGUIManager.openCraftGui(player,CraftType.COOKING)
            else -> throw InstructionParseException("Gui를 찾을 수 없습니다.")
        }
        return null
    }
}