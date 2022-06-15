package com.dreamstory.quest.events

import com.dreamstory.quest.Dialog
import com.dreamstory.quest.coroutine.schedule
import com.dreamstory.quest.main
import org.betonquest.betonquest.Instruction
import org.betonquest.betonquest.api.QuestEvent
import org.betonquest.betonquest.conversation.Conversation
import org.betonquest.betonquest.utils.PlayerConverter
import org.bukkit.Location
import org.bukkit.Sound
import org.bukkit.SoundCategory
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

class IntroEvent(instruction: Instruction): QuestEvent(instruction,true) {
    override fun execute(playerID: String?): Void? {
        val player = PlayerConverter.getPlayer(playerID)
        val dialogs = listOf(
            "이곳은 §d§l꿈§r. 무의식의 세계.",
            "두려워 말아요. 당신은 이 칠흑 같은 세계에서 곧 깨어날 거예요.",
            "하지만 깨어날 그곳이 당신이 원하던 곳 일지는 모르겠네요.",
            "어쩌면 당신이 원하는 게 무엇인지조차 잊어버렸을지도요.",
            "그렇지만 당신은 그곳에서 많은 §a§l인연§f을 만나고,",
            "당신만의 §a§l섬§f을 아름답게 꾸미기도 하고,",
            "때로는 위험천만한 §a§l모험§f에 몸을 맡기기도 하며,",
            "잊지 못할 §e§l추억§r들을 만들어 나갈 거예요.",
            "지금 당신이 이 §d§l꿈§r에서 깨어나려 하고 있어요.",
            "우리에게 주어진 시간은 여기까지 인가 봐요.",
            "하지만 이것만은 기억하길 바라요.",
            "당신이 이 짧은 §d§l꿈§r에서 깨어나더라도,",
            "또다시 새로운 §d§l꿈§r을 피워낼 것이란걸.",
        )

        main.schedule { // 커맨드막기 (CommandEvent 캔슬시키기)
            player.teleport(Location(main.server.worlds.first(),-41.5,33.0,-238.0,190f,20f))
            player.playSound(player.location, Sound.MUSIC_OVERWORLD_JAGGED_PEAKS, SoundCategory.PLAYERS,1f,1f)
            player.addPotionEffect(PotionEffect(PotionEffectType.BLINDNESS,1520,1,false,false,false))
            player.addPotionEffect(PotionEffect(PotionEffectType.INVISIBILITY,1520,10,false,false,false))
            player.addPotionEffect(PotionEffect(PotionEffectType.WATER_BREATHING,1520,10,false,false,false))

            Dialog.onDialog(player)
            Dialog.lockingView(player)
            waitFor(20)
            waitFor(120)
            Dialog.onTitleCutscene(player, dialogs, 100, Sound.ITEM_BOOK_PAGE_TURN)
            waitFor(((dialogs.size-1) * 100 + 60).toLong())
            player.playSound(player.location, Sound.BLOCK_PORTAL_TRIGGER,1f,1f)
            player.addPotionEffect(PotionEffect(PotionEffectType.CONFUSION,150,1,false,false,false))
            waitFor(60)
            player.teleport(Location(main.server.worlds.first(),-308.5,43.0,-28.5,90f,-40f))
            Dialog.offDialog(player)
            waitFor(100)

            Conversation(playerID,"Tutorial.monolog",player.location)
        }
        return null
    }

}