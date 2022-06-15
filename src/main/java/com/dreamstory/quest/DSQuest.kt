package com.dreamstory.quest

import com.dreamstory.quest.conditions.IsOnGround
import com.dreamstory.quest.events.IntroEvent
import com.dreamstory.quest.events.OpenGuiEvent
import com.dreamstory.quest.events.TeleportNpcEvent
import com.dreamstory.quest.objectives.AbilityBlockObjective

import com.dreamstory.quest.objectives.TestObjective
import org.betonquest.betonquest.BetonQuest
import org.bukkit.plugin.java.JavaPlugin

class DSQuest : JavaPlugin() {
    override fun onEnable() {
        main = this
        betonQuest = BetonQuest.getInstance()
        main.server.pluginManager.registerEvents(Listener, main)
        getCommand("questTest")?.setExecutor(Command())
        betonQuest.registerConditions("isOnGround", IsOnGround::class.java)
        betonQuest.registerObjectives("testObjective", TestObjective::class.java)
        betonQuest.registerObjectives("abilityBlock", AbilityBlockObjective::class.java)
        betonQuest.registerEvents("teleportNpc", TeleportNpcEvent::class.java)
        betonQuest.registerEvents("intro", IntroEvent::class.java)
        betonQuest.registerEvents("openGui", OpenGuiEvent::class.java)
    }
}