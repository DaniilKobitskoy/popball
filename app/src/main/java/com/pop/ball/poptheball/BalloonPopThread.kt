package com.pop.ball.poptheball

import android.content.Context
import android.view.SurfaceHolder

class BalloonPopThread(private val surfaceHolder: SurfaceHolder?, private val context: Context) : Thread() {
    private var running = false

    fun requestStop() {
        running = false
    }

    override fun run() {
        running = true
        while (running) {
            // update game state
            // draw game state
        }
    }
}
