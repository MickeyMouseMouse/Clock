import javafx.scene.canvas.Canvas
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.image.ImageView
import javafx.scene.shape.Circle
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javafx.scene.control.Hyperlink

class Controller {
    // циферблат
    val image = ImageView("clock.png")

    // минутная стрелка
    val minuteHand = Canvas(30.0, 320.0)

    // часовая стрелка
    val hourHand = Canvas(30.0, 200.0)

    // "пимпочка"
    val circle = Circle(17.0)

    // надпись со временем
    val labelTime = Label()

    // кнопка "Info"
    val buttonInfo = Button("Info")

    // кнопка "Back"
    val buttonBack = Button("Back")

    // надпись с информацией 1
    val labelInfo1 = Label("Application \"Clock\" by Andrew Jeus Version 1.0 (24 June 2019)")

    // надпись с информацией 2
    val labelInfo2 = Label("You can adjust the time manually: press (Shift +) H to adjust the number of hours; press (Shift +) M to adjust the number of minutes; press R to see the real time.")

    // надпись, указывающая на то, что показывается установленное пользователем время
    val labelUserTime = Label("User Time")

    // ссылка на github
    var hyperlink = Hyperlink("View code in GitHub")

    // время, которое нужно показать стрелками
    var hours = 0
    var minutes = 0

    fun update() {
        if (!labelUserTime.isVisible) {
            hours = LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("hh")).toInt() % 12
            minutes = LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("mm")).toInt() % 60
        }

        // 1 минута = 6 градусов
        minuteHand.rotate = 6.0 * minutes
        // 1 час = 30 градусов (5 минут) + добавка
        hourHand.rotate = 30.0 * hours + minutes / 2

        // обновление индикации времени в label
        labelTime.text = "$hours:$minutes"
        }
}