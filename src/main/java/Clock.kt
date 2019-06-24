import javafx.animation.AnimationTimer
import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.image.Image
import javafx.scene.layout.Pane
import javafx.scene.paint.Color
import javafx.scene.text.Font
import javafx.stage.Stage
import javafx.scene.input.KeyCode
import javafx.scene.layout.Border
import javafx.scene.text.TextAlignment
import java.awt.Desktop
import java.net.URI

// Визуализация стрелочных часов (системное время/настройка времени)
class Clock : Application() {
    private val controller = Controller()

    override fun start(stage: Stage) {
        // установка иконки приложения
        stage.icons.add(Image("icon.png"))

        // настройка окна
        stage.title = "Clock"
        stage.isResizable = false

        // минутная стрелка
        val pictureMinuteHand = controller.minuteHand.graphicsContext2D
        val minuteX = doubleArrayOf(5.0, 15.0, 25.0, 20.0, 20.0, 10.0, 10.0)
        val minuteY = doubleArrayOf(30.0, 0.0, 30.0, 30.0, 160.0, 160.0, 30.0)
        pictureMinuteHand.strokePolygon(minuteX, minuteY,minuteX.size)
        controller.minuteHand.layoutX = 230.0
        controller.minuteHand.layoutY = 75.0

        // часовая стрелка
        val pictureHourHand = controller.hourHand.graphicsContext2D
        val hourX = doubleArrayOf(0.0, 15.0, 30.0, 20.0, 20.0, 10.0, 10.0)
        val hourY = doubleArrayOf(30.0, 0.0, 30.0, 30.0, 100.0, 100.0, 30.0)
        pictureHourHand.fillPolygon(hourX, hourY, hourX.size)
        controller.hourHand.layoutX = 230.0
        controller.hourHand.layoutY = 135.0

        // "пимпочка"
        controller.circle.fill = Color.DIMGREY
        controller.circle.centerX = 244.0
        controller.circle.centerY = 238.0

        // надпись со временем
        controller.labelTime.layoutX = 400.0
        controller.labelTime.layoutY = 20.0
        controller.labelTime.font = Font(20.0)

        // кнопка "Info"
        controller.buttonInfo.layoutX = 425.0
        controller.buttonInfo.layoutY = 440.0

        // надпись, указывающая на то, что показывается установленное пользователем время
        controller.labelUserTime.layoutX = 15.0
        controller.labelUserTime.layoutY = 20.0
        controller.labelUserTime.font = Font(20.0)
        controller.labelUserTime.textFill = Color.RED
        controller.labelUserTime.isVisible = false

        // создание главных Панели и Сцены
        val pane = Pane()
        val scene = Scene(pane)

        // добавление элементов на сцену и показ
        pane.children.add(controller.image)
        pane.children.add(controller.minuteHand)
        pane.children.add(controller.hourHand)
        pane.children.add(controller.circle)
        pane.children.add(controller.labelTime)
        pane.children.add(controller.buttonInfo)
        pane.children.add(controller.labelUserTime)
        stage.scene = scene
        stage.show()


        // еще одна панель и сцена для вывода информации
        val paneInfo = Pane()
        val sceneInfo = Scene(paneInfo, scene.width,scene.height)

        // надпись с информацией 1
        controller.labelInfo1.layoutX = 70.0
        controller.labelInfo1.layoutY = 80.0
        controller.labelInfo1.font = Font(20.0)
        controller.labelInfo1.isWrapText = true
        controller.labelInfo1.maxWidth = 340.0
        controller.labelInfo1.textAlignment = TextAlignment.CENTER

        // надпись с информацией 2
        controller.labelInfo2.layoutX = 50.0
        controller.labelInfo2.layoutY = 250.0
        controller.labelInfo2.font = Font(20.0)
        controller.labelInfo2.isWrapText = true
        controller.labelInfo2.maxWidth = 375.0
        controller.labelInfo2.textAlignment = TextAlignment.CENTER

        // ссылка на github
        controller.hyperlink.layoutX = 170.0
        controller.hyperlink.layoutY = 135.0
        controller.hyperlink.style = "-fx-text-fill:black;"
        controller.hyperlink.isUnderline = true
        controller.hyperlink.border = Border.EMPTY

        // кнопка "Back"
        controller.buttonBack.layoutX = 420.0
        controller.buttonBack.layoutY = 440.0

        paneInfo.children.add(controller.labelInfo1)
        paneInfo.children.add(controller.labelInfo2)
        paneInfo.children.add(controller.hyperlink)
        paneInfo.children.add(controller.buttonBack)


        // таймер для перерисовки интерфейса
        object: AnimationTimer() {
            override fun handle(now: Long) {
                controller.update()
            }
        }.start()

        // события на нажатия кнопок клавиатуры ((Shift) + H, M, R)
        scene.setOnKeyPressed {
            when (it.code) {
                // показать реальное время
                KeyCode.R -> {
                    controller.labelUserTime.isVisible = false
                }

                // плюс/минус один час
                KeyCode.H -> {
                    controller.labelUserTime.isVisible = true

                    if (it.isShiftDown) {
                        if (--controller.hours == -1)
                            controller.hours = 11
                    } else {
                        if (++controller.hours == 12)
                            controller.hours = 0
                    }
                }

                // плюс/минус одна минута
                KeyCode.M -> {
                    controller.labelUserTime.isVisible = true

                    if (it.isShiftDown) {
                        if (--controller.minutes == -1) {
                            controller.minutes = 59
                            if (--controller.hours == -1)
                                controller.hours = 11
                        }
                    } else {
                        if (++controller.minutes == 60) {
                            controller.minutes = 0
                            if (++controller.hours == 12)
                                controller.hours = 0
                        }
                    }
                }

                else -> {}
            }
        }

        // показать вторую сцену с информацией
        controller.buttonInfo.setOnMouseClicked { stage.scene = sceneInfo }

        // закрыть вторую сцену
        controller.buttonBack.setOnMouseClicked { stage.scene = scene }

        // открыть github
        controller.hyperlink.setOnMouseClicked {
                Desktop.getDesktop().browse(URI("https://github.com/MickeyMouseMouse/Clock"))
        }
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            launch(Clock::class.java)
        }
    }
}