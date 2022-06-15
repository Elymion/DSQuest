package com.dreamstory.quest.objectives

import com.destroystokyo.paper.event.player.PlayerJumpEvent
import com.dreamstory.quest.main
import org.betonquest.betonquest.Instruction
import org.betonquest.betonquest.api.Objective
import org.betonquest.betonquest.utils.PlayerConverter
import org.bukkit.event.EventHandler
import org.bukkit.event.HandlerList
import org.bukkit.event.Listener


open class TestObjective(instruction: Instruction): Objective(instruction), Listener {
    val times = instruction.int
    init { template = TestDate::class.java }
    override fun start() {
        main.server.pluginManager.registerEvents(this, main)
    }
    override fun stop() {
        HandlerList.unregisterAll(this)
    }
    override fun getDefaultDataInstruction() = times.toString()
    override fun getProperty(name: String?, playerID: String?) = ""
    private fun getTestData(playerId: String) = dataMap[playerId] as TestDate

    @EventHandler
    fun onJump(event: PlayerJumpEvent){
        val playerID = PlayerConverter.getID(event.player)
        if (containsPlayer(playerID) && checkConditions(playerID)) {
            val data = getTestData(playerID)
            data.change(-1)
            if (data.isComplete()) completeObjective(playerID)
        }
    }
    protected class TestDate(ins:String, playerID: String, objId: String): ObjectiveData(ins,playerID,objId) {
        private var targetAmount = 0
        private var amountLeft = 0
        init {
            val array = ins.split(";")
            when (array.size) {
                1 -> {
                    targetAmount = ins.toInt()
                    amountLeft = targetAmount
                }
                2 -> {
                    targetAmount = array[0].toInt()
                    amountLeft = array[1].toInt()
                }
                else -> throw IllegalArgumentException("Invalid instruction string: $instruction")
            }
        }
        fun isComplete(): Boolean {
            return amountLeft <= 0
        }
        fun change(amount: Int): ObjectiveData {
            amountLeft += amount
            update()
            return this
        }
        override fun toString(): String {
            return java.lang.String.join(";", targetAmount.toString(), amountLeft.toString())
        }
    }
}

