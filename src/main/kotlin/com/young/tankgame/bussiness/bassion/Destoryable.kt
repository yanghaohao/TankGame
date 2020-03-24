package com.young.tankgame.bussiness.bassion

import com.young.tankgame.bussiness.model.View

interface Destoryable : View{

    fun isDestoryed():Boolean

    fun showDestroy():Array<View>?{
        return null
    }
}