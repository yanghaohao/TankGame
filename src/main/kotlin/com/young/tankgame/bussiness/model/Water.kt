package com.young.tankgame.bussiness.model

import com.young.tankgame.bussiness.bassion.Blockable
import com.young.tankgame.bussiness.Config
import org.itheima.kotlin.game.core.Painter

class Water(override val x: Int, override val y: Int) : Blockable {

    override val width: Int = Config.block
    override val height: Int = Config.block

    override fun draw() {
        Painter.drawImage("img/water.gif",x,y)
    }
}