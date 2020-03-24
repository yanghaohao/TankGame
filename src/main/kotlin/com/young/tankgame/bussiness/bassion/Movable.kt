package com.young.tankgame.bussiness.bassion

import com.young.tankgame.bussiness.Config
import com.young.tankgame.bussiness.enums.Direction
import com.young.tankgame.bussiness.model.View

interface Movable : View{

    /**
     * 可移动的方向
     */
    val currentDirection:Direction
    /**
     * 可移动物体的移动的速度
     */
    val speed:Int
    /**
     * 判断移动的物体是否和阻塞物体发生碰撞
     *
     * @return 阻挡的方向，如果为空，说明没有碰撞
     */
    fun willCollision(block: Blockable):Direction?{
        var x = this.x
        var y = this.y

        when(currentDirection){
            Direction.UP -> y -= speed
            Direction.DOWN -> y += speed
            Direction.LEFT -> x -= speed
            Direction.RIGHT -> x += speed
        }

        if (x < 0) return Direction.LEFT
        if (x > Config.gameWidth - width) return Direction.RIGHT
        if (y < 0) return Direction.UP
        if (y > Config.gameHeight - height) return Direction.DOWN

        val collision = checkCollision(block.x, block.y, block.width, block.height, x, y, width, height)

        return if (collision) currentDirection else null
    }

    fun notifyBlock(direction: Direction?,block: Blockable?)
}