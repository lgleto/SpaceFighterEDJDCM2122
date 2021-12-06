package ipca.example.spacefighter

import android.content.Context
import java.util.*

class Star {

    var x : Float
    var y : Float
    var speed : Int = 0

    var maxY : Float
    var minY : Float
    var maxX : Float
    var minX : Float

    constructor( screenWidth: Int, screenHeight : Int){
        maxX = screenWidth.toFloat()
        minX = 0F
        maxY = screenHeight.toFloat()
        minY = 0F

        var generator = Random()
        speed = generator.nextInt(10)

        x = generator.nextInt(maxX.toInt()).toFloat()
        y = generator.nextInt(maxY.toInt()).toFloat()

    }

    fun update(playerSpeed : Int){
        x -= playerSpeed
        x -= speed

        if (x<0){
            x = maxX
            var generator = Random()
            speed = generator.nextInt(10)
            y = generator.nextInt(maxY.toInt()).toFloat()
        }
    }

    fun starWidth() : Float{
        var generator = Random()
        return 1f + generator.nextFloat()*20f
    }

}