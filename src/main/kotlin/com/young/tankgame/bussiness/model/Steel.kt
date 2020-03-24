package com.young.tankgame.bussiness.model

import com.young.tankgame.bussiness.bassion.Attackable
import com.young.tankgame.bussiness.bassion.Blockable
import com.young.tankgame.bussiness.Config
import com.young.tankgame.bussiness.bassion.Sufferable
import org.itheima.kotlin.game.core.Composer
import org.itheima.kotlin.game.core.Painter

class Steel(override val x: Int, override val y: Int) : Blockable,
    Sufferable {
    override var blood: Int = Int.MAX_VALUE

    override fun notifySuffer(attackable: Attackable): Array<View>? {
        blood -= attackable.attackPower

        Composer.play("snd/hit.wav")
        return arrayOf(Blast(x,y))
    }

    override val width: Int = Config.block
    override val height: Int = Config.block

    override fun draw() {
        Painter.drawImage("img/steel.gif",x,y)
    }
}