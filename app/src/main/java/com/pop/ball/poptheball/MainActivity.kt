package com.pop.ball.poptheball

import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.pop.ball.poptheball.databinding.ActivityMainBinding

lateinit var balloonPopView: BalloonPopView

class MainActivity : AppCompatActivity() {
lateinit var bindingm: ActivityMainBinding

    private fun setMyContentView() {
        bindingm = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_main)
    }


    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        balloonPopView = BalloonPopView(this)
        setMyContentView()



        var anim = AnimationUtils.loadAnimation(this, R.anim.animashion)
        var txt = findViewById<TextView>(R.id.textView).startAnimation(anim)

        anim.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(p0: Animation?) {

            }

            override fun onAnimationEnd(p0: Animation?) {

bindingm.textView.visibility = View.GONE
                changeContentView()
            }

            override fun onAnimationRepeat(p0: Animation?) {

            }
        })
    }




    private fun changeContentView() {
        setContentView(balloonPopView)
    }
    override fun onResume() {
        super.onResume()
        if (!isThreadRunning) {
            isThreadRunning = true
            val myThread = Thread(Runnable {
                // code to run in the thread
                // ...
                balloonPopView.start()
                isThreadRunning = false
            })
            myThread.start()
        }

    }

    override fun onPause() {
        super.onPause()
        balloonPopView.stop()
    }
}
