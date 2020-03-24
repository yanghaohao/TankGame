package com.young.tankgame.bussiness.bassion

import com.young.tankgame.bussiness.model.View

interface Sufferable : View {

    val blood:Int

    fun notifySuffer(attackable: Attackable) : Array<View>?
}