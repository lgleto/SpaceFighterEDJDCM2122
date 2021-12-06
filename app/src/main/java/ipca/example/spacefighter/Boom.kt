package ipca.example.spacefighter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Rect
import java.util.*
import kotlin.math.max

class Boom {

    var bitmap : Bitmap
    var x : Float
    var y : Float


    constructor(context: Context, screenWidth: Int, screenHeight : Int){

        y = -250f
        x = -250f

        bitmap = BitmapFactory.decodeResource(context.resources,R.drawable.boom)


    }



}