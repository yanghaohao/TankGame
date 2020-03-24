import javafx.application.Application
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent
import javafx.scene.paint.Color
import org.itheima.kotlin.game.core.Composer
import org.itheima.kotlin.game.core.Painter
import org.itheima.kotlin.game.core.Window

class MyWindow : Window() {

    /**
     * 窗体创建
     */
    override fun onCreate() {
        println("窗体创建..")
    }

    /**
     * 窗体渲染的时候回调，不停地执行
     */
    override fun onDisplay() {
        Painter.drawImage("tank_u.gif",200,200)

        Painter.drawColor(Color.WHITE,20,20,100,100)

        Painter.drawText("你好",30,30)
    }

    /**
     *  按键的时候
     */
    override fun onKeyPressed(event: KeyEvent) {
        when(event.code){
            KeyCode.ENTER -> print("点击了enter按钮")
            KeyCode.L -> Composer.play("blast.wav")
        }
    }

    /**
     * 刷新，做业务逻辑，做耗时操作
     */
    override fun onRefresh() {
    }
}

fun main(args: Array<String>) {
    Application.launch(MyWindow::class.java)
}