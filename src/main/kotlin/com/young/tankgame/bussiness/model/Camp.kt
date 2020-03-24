package com.young.tankgame.bussiness.model

import com.young.tankgame.bussiness.*
import com.young.tankgame.bussiness.bassion.Attackable
import com.young.tankgame.bussiness.bassion.Blockable
import com.young.tankgame.bussiness.bassion.Destoryable
import com.young.tankgame.bussiness.bassion.Sufferable
import org.itheima.kotlin.game.core.Painter


class Camp(override val x: Int, override val y: Int) : View, Blockable,
    Sufferable, Destoryable {


    override var blood: Int = 10

    override fun notifySuffer(attackable: Attackable): Array<View>? {
        blood -= attackable.attackPower
        if (blood == 3 || blood == 6){

        }

        return arrayOf(Blast(x,y))
    }

    override val width: Int = Config.block * 2
    override val height: Int = Config.block + 32
    override fun draw() {
        Painter.drawImage("img/steel_small.gif",x,y)
        Painter.drawImage("img/steel_small.gif",x+32,y)
        Painter.drawImage("img/steel_small.gif",x+64,y)
        Painter.drawImage("img/steel_small.gif",x+96,y)
        Painter.drawImage("img/steel_small.gif",x,y+32)
        Painter.drawImage("img/steel_small.gif",x,y+64)
        Painter.drawImage("img/steel_small.gif",x+96,y+32)
        Painter.drawImage("img/steel_small.gif",x+96,y+64)
        Painter.drawImage("img/camp.gif",x+32,y+32)
    }

    override fun isDestoryed(): Boolean = blood <= 0

    override fun showDestroy(): Array<View>? {
        return arrayOf(Blast(x - 32,y - 32)
                ,Blast(x,y - 32)
                ,Blast(x+32,y - 32)
                ,Blast(x-32,y)
                ,Blast(x,y)
                ,Blast(x+32,y)
                ,Blast(x-32,y+32)
                ,Blast(x,y+32)
                ,Blast(x+32,y+32))
    }
}