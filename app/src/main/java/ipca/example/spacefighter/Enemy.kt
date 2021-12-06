package ipca.example.spacefighter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Rect
import java.util.*
import kotlin.math.max

class Enemy {

    var bitmap : Bitmap
    var x : Float
    var y : Float
    var speed : Int = 0

    var maxY : Float
    var minY : Float
    var maxX : Float
    var minX : Float

    var detectColosion = Rect()

    constructor(context: Context, screenWidth: Int, screenHeight : Int){
        maxX = screenWidth.toFloat()
        minX = 0F
        maxY = screenHeight.toFloat()
        minY = 0F

        bitmap = BitmapFactory
            .decodeResource(context.resources,R.drawable.enemy)

        var generator = Random()
        speed = generator.nextInt(6) + 10
        y = generator.nextInt(maxY.toInt()).toFloat() - bitmap.height
        x = maxX

        detectColosion = Rect(x.toInt(),y.toInt(),bitmap.width, bitmap.height)
    }

    fun update(playerSpeed:Int){
        x -= playerSpeed
        x -= speed

        detectColosion.left = x.toInt()
        detectColosion.top = y.toInt()
        detectColosion.right = x.toInt() + bitmap.width
        detectColosion.bottom = y.toInt() + bitmap.height

        if (x<minX - bitmap.width){
            var generator = Random()
            speed = generator.nextInt(6) + 10
            y = generator.nextInt(maxY.toInt()).toFloat() - bitmap.height
            x = maxX
        }
    }

}