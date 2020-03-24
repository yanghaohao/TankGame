package com.young.tankgame.bussiness.bassion

import com.young.tankgame.bussiness.enums.Direction
import com.young.tankgame.bussiness.model.View

interface AutoMovable : View{

    val currentDirection: Direction

    val speed:Int

    fun autoMove()
}