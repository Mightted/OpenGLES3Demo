package com.hxs.opengles3demo

import android.opengl.GLSurfaceView
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val view = GLSurfaceView(this)
        view.setEGLContextClientVersion(3)

        view.setRenderer(MyRenderer())
        setContentView(view)

    }
}
