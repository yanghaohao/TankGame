package com.young.tankgame.bussiness

import com.young.tankgame.bussiness.bassion.*
import com.young.tankgame.bussiness.enums.Direction
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent
import com.young.tankgame.bussiness.model.*
import org.itheima.kotlin.game.core.Window
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader
import java.util.concurrent.CopyOnWriteArrayList

class GameWindow : Window(title = "坦克大战1.0",icon = "img/logo.jpg"
    ,height = Config.gameHeight,width = Config.gameWidth) {

//    private val views = arrayListOf<View>()
    private val views = CopyOnWriteArrayList<View>()
    private lateinit var tank:Tank
    private var gameOver:Boolean = false
    private var enemyTotalSize = 20
    private var enemyActiveSize = 20
    private val enemyBornLocation = arrayListOf<Pair<Int,Int>>()
    private var bornIndex = 0

    override fun onCreate() {
        val resourceAsStream = javaClass.getResourceAsStream("/map/1.map")
        val reader = BufferedReader(InputStreamReader(resourceAsStream,"utf-8"))

        val lines = reader.readLines()

        var lineNumber = 0
        lines.forEach {
            var column = 0
            it.toCharArray().forEach {
                when(it){
                    '砖' -> views.add(Wall(column * Config.block,lineNumber * Config.block))
                    '铁' -> views.add(Steel(column * Config.block,lineNumber * Config.block))
                    '水' -> views.add(Water(column * Config.block,lineNumber * Config.block))
                    '草' -> views.add(Grass(column * Config.block,lineNumber * Config.block))
                    '敌' -> enemyBornLocation.add(Pair(column * Config.block,lineNumber * Config.block))
                }
                column++
            }
            lineNumber++
        }

        tank = Tank(Config.block * 10, Config.block * 12)
        views.add(tank)

        views.add(Camp(Config.gameWidth / 2 - Config.block, Config.gameHeight - 96))
    }

    override fun onDisplay() {
        views.forEach {
            it.draw()
        }
    }

    override fun onKeyPressed(event: KeyEvent) {
        if (gameOver){
            return
        }

        when(event.code){
            KeyCode.UP -> {
                tank.move(Direction.UP)
            }
            KeyCode.DOWN -> {
                tank.move(Direction.DOWN)
            }
            KeyCode.LEFT -> {
                tank.move(Direction.LEFT)
            }
            KeyCode.RIGHT -> {
                tank.move(Direction.RIGHT)
            }
            KeyCode.X -> {
                val bullet = tank.shot()
                views.add(bullet)
            }
        }
    }

    override fun onRefresh() {

        views.filterIsInstance<Destoryable>().forEach {
            if (it.isDestoryed()){
                views.remove(it)

                if (it is Enemy){
                    enemyTotalSize--
                }

                val destroy = it.showDestroy()
                destroy?.let {
                    views.addAll(destroy)
                }
            }
        }

        if (gameOver){
            return
        }

        views.filterIsInstance<Movable>().forEach { move ->
            var badDirection:Direction? = null
            var badBlock: Blockable? = null
            views.filter { (it is Blockable)  and (move != it)  }.forEach blockTag@{ block ->
                block as Blockable

                val direction = move.willCollision(block)

                direction?.let {
                    badDirection = direction
                    badBlock = block
                    return@blockTag
                }
            }
            move.notifyBlock(badDirection,badBlock)
        }

        views.filterIsInstance<AutoMovable>().forEach { move -> move.autoMove() }


        views.filterIsInstance<Attackable>().forEach { attack ->
            views.filter { (it is Sufferable) and (attack.owner != it) }.forEach sufferTag@{ suffer ->
                suffer as Sufferable

                if(attack.isCollision(suffer)){
                    attack.notifyAttack(suffer)

                    val sufferView = suffer.notifySuffer(attack)
                    sufferView?.let {
                        views.addAll(sufferView)
                    }

                    return@sufferTag
                }
            }
        }

        views.filterIsInstance<AutoShot>().forEach { autoShot ->

            val shot = autoShot.autoShot()
            shot?.let {
                views.add(shot)
            }

        }

        if (views.filterIsInstance<Camp>().isEmpty()){
            gameOver = true
        }

        if ((views.filterIsInstance<Enemy>().isEmpty()) or (enemyTotalSize <= 0)){
            gameOver = true
        }

        if ((enemyTotalSize > 0) and (views.filterIsInstance<Enemy>().size < enemyActiveSize)){
            val index = bornIndex % enemyBornLocation.size
            val pair = enemyBornLocation[index]
            views.add(Enemy(pair.first,pair.second))

            bornIndex++
        }
    }
}