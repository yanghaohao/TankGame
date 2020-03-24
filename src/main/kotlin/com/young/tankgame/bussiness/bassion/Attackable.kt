package com.young.tankgame.bussiness.bassion

import com.young.tankgame.bussiness.model.View

interface Attackable : View {

    val owner:View
    val attackPower:Int

    fun isCollision(sufferable: Sufferable) : Boolean
    fun notifyAttack(sufferable: Sufferable)
}