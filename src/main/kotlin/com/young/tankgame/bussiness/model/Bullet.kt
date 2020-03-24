package com.young.tankgame.bussiness.model

import com.young.tankgame.bussiness.*
import com.young.tankgame.bussiness.bassion.Attackable
import com.young.tankgame.bussiness.bassion.AutoMovable
import com.young.tankgame.bussiness.bassion.Destoryable
import com.young.tankgame.bussiness.bassion.Sufferable
import com.young.tankgame.bussiness.enums.Direction
import com.young.tankgame.bussiness.ext.checkCollision
import org.itheima.kotlin.game.core.Painter


class Bullet(override val currentDirection : Direction, create:(width:Int, height:Int) -> Pair<Int,Int>,
             override val owner: View
) : AutoMovable, Destoryable,
    Attackable {
    override val attackPower: Int = 1

    override val speed: Int = 8

    override var x: Int = 0
    override var y: Int = 0

    override val width: Int
    override val height: Int
    private var isDestoryed = false
    private val imagePath= when (currentDirection) {
        Direction.UP -> "img/bullet_u.gif"
        Direction.DOWN -> "img/bullet_d.gif"
        Direction.LEFT -> "img/bullet_l.gif"
        Direction.RIGHT -> "img/bullet_r.gif"
    }

    init {
        val size = Painter.size(imagePath!!)
        width = size[0]
        height = size[1]

        val pair = create.invoke(width,height)
        x = pair.first
        y = pair.second
    }

    override fun draw() {
        Painter.drawImage(imagePath!!,x,y)
    }

    override fun autoMove() {

        when(currentDirection){
            Direction.UP -> y -= speed
            Direction.DOWN -> y += speed
            Direction.LEFT -> x -= speed
            Direction.RIGHT -> x += speed
        }
    }

    override fun isDestoryed(): Boolean {
        if (isDestoryed) return true
        return when {
            x < -width -> true
            x > Config.gameWidth + width -> true
            y < -height -> true
            y > Config.gameHeight + height -> true
            else -> false
        }

    }

    override fun isCollision(sufferable: Sufferable): Boolean {
        return checkCollision(sufferable)
    }

    override fun notifyAttack(sufferable: Sufferable) {

        isDestoryed = true
    }
}