package com.dreamstory.quest.objectives

import com.dreamstory.ability.ability.event.AbilityBlockBreakEvent
import com.dreamstory.ability.ability.play.block.AbilityObject
import com.dreamstory.ability.ability.play.block.obj.BreakAbleBlock
import com.dreamstory.ability.manager.AbilityBlockManager
import com.dreamstory.quest.main
import org.betonquest.betonquest.Instruction
import org.betonquest.betonquest.api.CountingObjective
import org.betonquest.betonquest.compatibility.mmogroup.mmolib.MythicLibIntegrator
import org.betonquest.betonquest.compatibility.mythicmobs.MythicMobKillObjective
import org.betonquest.betonquest.exceptions.InstructionParseException
import org.betonquest.betonquest.utils.PlayerConverter
import org.bukkit.Material
import org.bukkit.Particle
import org.bukkit.event.EventHandler
import org.bukkit.event.HandlerList
import org.bukkit.event.Listener

class AbilityBlockObjective(instruction: Instruction): CountingObjective(instruction), Listener {
    private val abilityBlock: AbilityObject
    init {
        val tag = instruction.next()
        abilityBlock = AbilityBlockManager.getAbilityBlock(tag) ?: throw InstructionParseException("어빌리티 블럭을 찾을 수 없습니다: $tag")
        val number = instruction.int
        targetAmount = if (number < 1) throw InstructionParseException("잘못된 숫자입니다: $number") else number
    }
    override fun start() {
        main.server.pluginManager.registerEvents(this, main)
    }

    override fun stop() {
        HandlerList.unregisterAll(this)
    }
    @EventHandler
    fun onAbilityBlockBreak(event: AbilityBlockBreakEvent) {
        event.isCancelled = true
        val playerID = PlayerConverter.getID(event.player)
        if (containsPlayer(playerID) && abilityBlock == event.abilityObject && checkConditions(playerID)) {
            val block = event.block
            block.world.spawnParticle(Particle.EXPLOSION_NORMAL, block.location.add(.5, .5, .5), 0)
            BreakAbleBlock.startCoolTime(block, block.blockData, 60)
            println("aaa")
            block.type = Material.AIR
            getCountingData(playerID).progress()
            completeIfDoneOrNotify(playerID)
        }
    }
}