package com.hxs.opengles3demo

import android.opengl.GLES20.*
import android.opengl.GLSurfaceView
import android.opengl.Matrix
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class MyRenderer: GLSurfaceView.Renderer {

    private lateinit var box:Box
    private val projectMatrix = FloatArray(16)
    private val modelMatrix = FloatArray(16)

    override fun onDrawFrame(gl: GL10?) {
        glClear(GL_COLOR_BUFFER_BIT)

        box.enable()
        box.draw()
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        glViewport(0, 0, width, height)

        Matrix.perspectiveM(projectMatrix, 0, 45f, width.toFloat()/height, 1f, 10f)
        val temp = FloatArray(16)
        Matrix.setIdentityM(modelMatrix, 0)
        Matrix.translateM(modelMatrix, 0, 0f,0f,-2f)
        Matrix.rotateM(modelMatrix, 0,-30f, 1f,0f,0f)
        Matrix.multiplyMM(temp,0, projectMatrix, 0, modelMatrix, 0)

        System.arraycopy(temp, 0, projectMatrix, 0, temp.size)
//        box.setMatrix(projectMatrix)
//        println("矩阵$projectMatrix")

//        box.setMatrix(projectMatrix)


    }

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        glClearColor(0.2f, 0.3f, 0.3f, 1.0f)
        box = Box()
    }
}