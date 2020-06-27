package com.hxs.opengles3demo

import android.annotation.SuppressLint
import android.opengl.GLSurfaceView
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent

class MainActivity : AppCompatActivity() {
    private var currentX:Float = 0f
    private var currentY:Float = 0f

    private val renderer = MyRenderer()

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val view = GLSurfaceView(this)
        view.setEGLContextClientVersion(3)

        view.setRenderer(renderer)

        view.setOnTouchListener { v, event ->
            val normalizedX = event.x / v.width.toFloat() * 2 - 1
            val normalizedY = -(event.y / v.height.toFloat() * 2 - 1)
//            if (currentX == 0f && currentY == 0f) {
//                currentX = normalizedX
//                currentY = normalizedY
//            }
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    view.queueEvent {
                        currentX = normalizedX
                        currentY = normalizedY
                    }
                }
                MotionEvent.ACTION_MOVE -> {
                    view.queueEvent {
                        renderer.moveCamera(normalizedX - currentX, normalizedY - currentY)
//                        renderer.moveOnWidth(normalizedX - currentX)
//                        renderer.moveOnHeight(normalizedY - currentY)
                    }
                }
                MotionEvent.ACTION_UP -> {
                    view.queueEvent {
                        renderer.stopMove(normalizedX - currentX, normalizedY - currentY)
                    }
                }
            }
//            currentX = normalizedX
//            currentY = normalizedY
            true
        }
        setContentView(view)

    }

    override fun onResume() {
        super.onResume()
        renderer.onResume()
    }
}
