package com.young.tankgame.bussiness.model

import com.young.tankgame.bussiness.*
import com.young.tankgame.bussiness.bassion.*
import com.young.tankgame.bussiness.enums.Direction
import org.itheima.kotlin.game.core.Painter
import kotlin.random.Random

class Enemy(override var x: Int, override var y: Int) : Movable,
    AutoMovable, Blockable,
    AutoShot, Sufferable,
    Destoryable {

    override var blood: Int=2

    override val width: Int= Config.block
    override val height: Int = Config.block
    override var currentDirection: Direction = Direction.DOWN
    override val speed: Int = 8
    private var badDirection:Direction? = null
    private var lastShotTime = 0L
    private var shotFrequency = 1000
    private var lastMoveTime = 0L
    private var moveFrequency = 100

    override fun draw() {
        val imagePath = when (currentDirection) {
            Direction.UP -> "img/enemy_1_u.gif"
            Direction.DOWN -> "img/enemy_1_d.gif"
            Direction.LEFT -> "img/enemy_1_l.gif"
            Direction.RIGHT -> "img/enemy_1_r.gif"
        }

        Painter.drawImage(imagePath,x,y)
    }


    override fun notifyBlock(direction: Direction?, block: Blockable?) {
        this.badDirection = direction
    }

    override fun autoMove() {
        val currentTime = System.currentTimeMillis()
        if (currentTime - lastMoveTime < moveFrequency) return
        lastMoveTime = currentTime

        if (currentDirection == badDirection){
            currentDirection = randomDirection(badDirection!!)
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

    private fun randomDirection(badDirection:Direction?):Direction{
        val i= Random.nextInt(4)
        val direction= when(i){
            0 -> Direction.UP
            1 -> Direction.DOWN
            2 -> Direction.LEFT
            3 -> Direction.RIGHT
            else -> Direction.UP
        }

        if (direction == badDirection){
            return randomDirection(badDirection)
        }
        return direction
    }

    override fun autoShot(): View? {
        val currentTime = System.currentTimeMillis()
        if (currentTime - lastShotTime < shotFrequency) return null
        lastShotTime = currentTime

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
                    bulletY = tankY + (tankHeight - bulletHeight)/2
                }
            }
            Pair(bulletX,bulletY)
        },this)
    }

    override fun notifySuffer(attackable: Attackable): Array<View>? {
        if (attackable.owner is Enemy){
            return null
        }

        blood -= attackable.attackPower
        return arrayOf(Blast(x,y))
    }

    override fun isDestoryed(): Boolean = blood <= 0
}