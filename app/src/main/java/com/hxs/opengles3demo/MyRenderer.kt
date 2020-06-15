package com.hxs.opengles3demo

import android.opengl.GLES20.*
import android.opengl.GLSurfaceView
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class MyRenderer: GLSurfaceView.Renderer {

    private lateinit var box:Box

    override fun onDrawFrame(gl: GL10?) {
        glClear(GL_COLOR_BUFFER_BIT)

        box.enable()
        box.draw()
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        glViewport(0, 0, width, height)
    }

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        glClearColor(0.2f, 0.3f, 0.3f, 1.0f)
        box = Box()
    }
}