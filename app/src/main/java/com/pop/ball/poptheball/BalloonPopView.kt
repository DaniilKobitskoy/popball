package com.pop.ball.poptheball

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
lateinit var balloonPopThread: BalloonPopThread
var isThreadRunning = false

class BalloonPopView(context: Context) : SurfaceView(context), Runnable, SurfaceHolder.Callback {
    private val paint = Paint()
    private val balloons = mutableListOf<Balloon>()
    private var score = 0
    private var gameOver = false
    private val thread = Thread(this)

    init {
        holder.addCallback(this)
        setBackgroundColor(resources.getColor(R.color.white))  // add this line
        isFocusable = true
    }

    init {
        paint.textSize = 100f
        paint.textAlign = Paint.Align.CENTER
    }

    fun start() {
        isThreadRunning = true
        val myThread = Thread(Runnable {
            // code to run in the thread
            // ...
            thread.start()
            isThreadRunning = false
        })
        myThread.start()

    }

    override fun run() {
        if (!isThreadRunning) {
            isThreadRunning = true
            val myThread = Thread(Runnable {
                // code to run in the thread
                // ...
                while (!gameOver) {
                    val balloon = Balloon()
                    balloons.add(balloon)

                    try {
                        Thread.sleep(500)
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    }
                }
                isThreadRunning = false
            })
            myThread.start()
        }

    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawColor(resources.getColor(R.color.tooltip_background_light))

        for (balloon in balloons) {
            balloon.y += 5
            canvas.drawOval(
                RectF(
                    balloon.x - balloon.radius,
                    balloon.y - balloon.radius,
                    balloon.x + balloon.radius,
                    balloon.y + balloon.radius
                ), paint
            )
        }

        if (gameOver) {
            paint.color = resources.getColor(R.color.purple_200)
            canvas.drawText("Game Over", width / 2f, height / 2f, paint)
        } else {
            paint.color = resources.getColor(R.color.teal_200)
            canvas.drawText("Score: $score", width / 2f, 100f, paint)
        }

        invalidate()  // add this line
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            for (i in balloons.indices) {
                val balloon = balloons[i]
                if (Math.abs(event.x - balloon.x) <= balloon.radius &&
                    Math.abs(event.y - balloon.y) <= balloon.radius
                ) {
                    score++
                    balloons.removeAt(i)
                    break
                }
            }
        }

        return true
    }

    inner class Balloon {
        val x = (Math.random() * width).toFloat()
        var y = (Math.random() * height).toFloat()
        val radius = 100f
    }


    fun stop() {
        balloonPopThread.requestStop()
    }

    override fun surfaceCreated(p0: SurfaceHolder) {
        balloonPopThread = BalloonPopThread(holder, context)
        balloonPopThread.start()
    }

    override fun surfaceChanged(p0: SurfaceHolder, format: Int, width: Int, height: Int) {
    }

    override fun surfaceDestroyed(p0: SurfaceHolder) {
    }

}
