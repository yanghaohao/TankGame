package com.young.tankgame.bussiness.model

import com.young.tankgame.bussiness.*
import com.young.tankgame.bussiness.bassion.Attackable
import com.young.tankgame.bussiness.bassion.Blockable
import com.young.tankgame.bussiness.bassion.Destoryable
import com.young.tankgame.bussiness.bassion.Sufferable
import org.itheima.kotlin.game.core.Composer
import org.itheima.kotlin.game.core.Painter

/**
 * 砖墙
 */
class Wall(override val x: Int, override val y: Int) : Blockable,
    Sufferable, Destoryable {
    override var blood: Int = 1

    override val width: Int = Config.block
    override val height: Int = Config.block

    override fun draw() {
        Painter.drawImage("img/wall.gif",x,y)
    }

    override fun notifySuffer(attackable: Attackable) : Array<View>? {
        blood -= attackable.attackPower

        Composer.play("snd/hit.wav")

        return arrayOf(Blast(x,y))
    }

    override fun isDestoryed(): Boolean = blood <= 0

}