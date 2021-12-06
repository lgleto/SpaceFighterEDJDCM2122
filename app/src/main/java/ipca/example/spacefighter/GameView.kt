package ipca.example.spacefighter

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.media.MediaPlayer
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView

class GameView : SurfaceView, Runnable {

    var playing = false
    lateinit var gameThread : Thread


    lateinit var surfaceHolder: SurfaceHolder
    var canvas : Canvas? =  null
    lateinit var paint : Paint

    lateinit var player : Player
    lateinit var boom : Boom
    var stars = arrayListOf<Star>()
    var enemies = arrayListOf<Enemy>()

    constructor(context: Context?,
                screenWidth : Int,
                screenHeight:Int) : super(context){
        init(context,
            screenWidth,
            screenHeight)
    }
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs){
        init(context)
    }
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ){
        init(context)
    }

    fun init(context: Context?,
             screenWidth : Int = 0,
             screenHeight: Int = 0){
        surfaceHolder = holder
        player = Player(context!!, screenWidth, screenHeight)
        boom = Boom(context!!, screenWidth, screenHeight)
        paint = Paint()

        for(index in 0..99){
            stars.add(Star(screenWidth,screenHeight))
        }
        for(index in 0..2){
            enemies.add(Enemy(context, screenWidth,screenHeight))
        }
    }


    override fun run() {
        while(playing){
            update()
            draw()
            control()
        }
    }

    fun resume(){
        playing = true
        gameThread = Thread(this)
        gameThread.start()
    }

    fun pause() {
        playing = false
        gameThread.join()
    }

    fun update(){
        boom.x = -250f
        boom.y = -250f

        player.update()
        for( s in stars){
            s.update(player.speed)
        }
        for( e in enemies){
            e.update(player.speed)
            if (e.detectColosion.intersect(player.detectColosion)){

                boom.x = e.x
                boom.y = e.y
                e.x = - 300f
                var boomSound = MediaPlayer.create(context, R.raw.boom)
                boomSound!!.start()

            }
        }
    }

    fun draw(){
        if (surfaceHolder.surface.isValid){
            canvas = surfaceHolder.lockCanvas()
            canvas?.drawColor(Color.BLACK)

            paint.color = Color.YELLOW
            for (s in stars){
                paint.strokeWidth = s.starWidth()
                canvas?.drawPoint(s.x, s.y, paint)
            }
            for( e in enemies){
                canvas?.drawBitmap(e.bitmap, e.x, e.y, paint)
                //paint.color = Color.GREEN
                //paint.style = Paint.Style.STROKE
                //canvas?.drawRect(e.detectColosion,paint)
            }
            canvas?.drawBitmap(player.bitmap, player.x, player.y, paint)
            //paint.style = Paint.Style.STROKE
            //canvas?.drawRect(player.detectColosion,paint)

            canvas?.drawBitmap(boom.bitmap, boom.x, boom.y, paint)
            surfaceHolder.unlockCanvasAndPost(canvas)
        }
    }

    fun control(){
        Thread.sleep(17)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event?.let{
            when (it.action.and(MotionEvent.ACTION_MASK)){
                MotionEvent.ACTION_UP ->{
                    player.boosting = false
                }
                MotionEvent.ACTION_DOWN ->{
                    player.boosting = true
                }
            }
        }


        return true
    }
}