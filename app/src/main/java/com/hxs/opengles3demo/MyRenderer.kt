package com.hxs.opengles3demo

import android.opengl.GLES20.*
import android.opengl.GLSurfaceView
import android.opengl.Matrix
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10


const val TIME_FRAME: Float = 1000f / 60f

class MyRenderer : GLSurfaceView.Renderer {

    private lateinit var box: Box
    private lateinit var subBox: Box
    private val projectMatrix = FloatArray(16)
    private val modelMatrix = FloatArray(16)
    private val rotateMatrix = FloatArray(16)
    private val currentTime = System.currentTimeMillis()
    private var frames = 0


    private fun progress(): Int {
        val millis = System.currentTimeMillis() - currentTime
        if (millis >= frames * TIME_FRAME) {
            frames ++
        }
        return frames
    }

    override fun onDrawFrame(gl: GL10?) {
        glClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT)

        subBox.enable()
        Matrix.setIdentityM(rotateMatrix, 0)
        Matrix.rotateM(rotateMatrix, 0, progress() * 1.5f, 0.5f, 1f, 0f)
        box.rotateMatrix(rotateMatrix)
        box.draw()


        subBox.enable()
        Matrix.setIdentityM(rotateMatrix, 0)
        Matrix.rotateM(rotateMatrix, 0, progress() * 1.5f, 0.5f, 0f, 1f)
        subBox.rotateMatrix(rotateMatrix)
        subBox.draw()
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        glViewport(0, 0, width, height)

        Matrix.perspectiveM(projectMatrix, 0, 45f, width.toFloat() / height, 0.1f, 100f)
//        val temp = FloatArray(16)
//        Matrix.setIdentityM(modelMatrix, 0)
//        Matrix.translateM(modelMatrix, 0, 0f, 0f, -5f)
//        Matrix.multiplyMM(temp, 0, projectMatrix, 0, modelMatrix, 0)
//
//        System.arraycopy(temp, 0, projectMatrix, 0, temp.size)
//        box.setMatrix(projectMatrix)
//        println("矩阵$projectMatrix")

        box.setMatrix(translateM(-0.8f, 0f, -8f))
        subBox.setMatrix(translateM(0.8f, 0f, -8f))


    }


    private fun translateM(x:Float, y:Float, z:Float):FloatArray {
        val temp = FloatArray(16)
        Matrix.setIdentityM(modelMatrix, 0)
        Matrix.translateM(modelMatrix, 0, x, y, z)
        Matrix.multiplyMM(temp, 0, projectMatrix, 0, modelMatrix, 0)
        return temp
    }

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        glClearColor(0.2f, 0.3f, 0.3f, 1.0f)
        glEnable(GL_DEPTH_TEST)
        box = Box()
        subBox = Box()

    }
}