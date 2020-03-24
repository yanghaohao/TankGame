package com.young.tankgame.bussiness.model

import com.young.tankgame.bussiness.Config
import com.young.tankgame.bussiness.bassion.Destoryable
import org.itheima.kotlin.game.core.Painter

class Blast(override val x: Int, override val y: Int) : Destoryable {

    override val width: Int = Config.block
    override val height: Int = Config.block
    private val imagPaths = arrayListOf<String>()
    private var index:Int = 0

    init {
        (1..32).forEach {
            imagPaths.add("img/blast_$it.png")
        }
    }

    override fun draw() {
        var i = index % imagPaths.size

        Painter.drawImage(imagPaths[i],x,y)

        index++
    }

    override fun isDestoryed(): Boolean {
        return index >= imagPaths.size
    }
}