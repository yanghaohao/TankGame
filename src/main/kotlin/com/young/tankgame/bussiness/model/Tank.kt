package com.young.tankgame.bussiness.model

import com.young.tankgame.bussiness.*
import com.young.tankgame.bussiness.bassion.*
import com.young.tankgame.bussiness.enums.Direction
import org.itheima.kotlin.game.core.Painter

class Tank(override var x: Int, override var y: Int) : Movable,
    Sufferable, Blockable,
    Destoryable {
    override var blood: Int=20

    override fun notifyBlock(direction: Direction?, block: Blockable?) {
        this.badDirection = direction
    }

    private var badDirection:Direction? = null
    override val width: Int = Config.block
    override val height: Int = Config.block

    override var currentDirection:Direction = Direction.UP
    override val speed:Int = 8

    override fun draw() {
        val imagePath = when (currentDirection) {
            Direction.UP -> "img/tank_u.gif"
            Direction.DOWN -> "img/tank_d.gif"
            Direction.LEFT -> "img/tank_l.gif"
            Direction.RIGHT -> "img/tank_r.gif"
        }

        Painter.drawImage(imagePath,x,y)

    }

    fun move(direction: Direction){

        if (direction == badDirection){
            return
        }

        if (this.currentDirection != direction){
            this.currentDirection = direction
            return
        }

        when(currentDirection){
            Direction.UP -> y -= speed
            Direction.DOWN -> y += speed
            Direction.LEFT -> x -= speed
            Direction.RIGHT -> x += speed
        }

        if (x < 0) x = 0
        if (x > Config.gameWidth - width) x = Config.gameWidth - width
        if (y < 0) y = 0
        if (y > Config.gameHeight - height) y = Config.gameHeight - height
    }

    fun shot() : Bullet{
        return Bullet(currentDirection, { bulletWidth, bulletHeight ->
            var bulletX = 0
            var bulletY = 0

            val tankX = x
            val tankY = y
            val tankWidth = width
            val tankHeight = height


            when(currentDirection){
                Direction.UP -> {
                    bulletX = tankX + (tankWidth - bulletWidth)/2
                    bulletY = tankY - bulletHeight/2
                }
                Direction.DOWN -> {
                    bulletX = tankX + (tankWidth - bulletWidth)/2
                    bulletY = tankY + tankHeight - bulletHeight/2
                }
                Direction.LEFT -> {
                    bulletX = tankX - bulletWidth/2
                    bulletY = tankY + (tankHeight - bulletHeight)/2
                }
                Direction.RIGHT -> {
                    bulletX = tankX + tankWidth - bulletWidth/2
                    bulletY = tankY + (tankWidth - bulletWidth)/2
                }
            }
            Pair(bulletX,bulletY)
        },this)
    }

    override fun notifySuffer(attackable: Attackable): Array<View>? {
        blood -= attackable.attackPower
        return arrayOf(Blast(x,y))
    }

    override fun isDestoryed(): Boolean = blood <= 0
}