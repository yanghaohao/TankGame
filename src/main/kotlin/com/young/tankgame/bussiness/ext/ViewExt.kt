package com.young.tankgame.bussiness.ext

import com.young.tankgame.bussiness.model.View

fun View.checkCollision(view: View) : Boolean{

    return checkCollision(x,y,width,height,view.x,view.y,view.width,view.height)
}