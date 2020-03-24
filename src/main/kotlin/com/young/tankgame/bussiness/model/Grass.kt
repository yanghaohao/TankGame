package com.young.tankgame.bussiness.model

import com.young.tankgame.bussiness.Config
import org.itheima.kotlin.game.core.Painter

/**
 * 砖墙
 */
class Grass(override val x: Int, override val y: Int) : View{

    override val width: Int = Config.block
    override val height: Int = Config.block

    override fun draw() {
        Painter.drawImage("img/grass.gif",x,y)
    }
}