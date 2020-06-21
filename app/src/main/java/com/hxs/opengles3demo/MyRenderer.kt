package com.hxs.opengles3demo

import android.opengl.GLES20.*
import android.opengl.GLSurfaceView
import android.opengl.Matrix
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10


const val TIME_FRAME: Float = 1000f / 60f

class MyRenderer : GLSurfaceView.Renderer {

    private lateinit var box: Box
    private lateinit var box1: Box
    private lateinit var box2: Box
    private lateinit var box3: Box
    private val projectMatrix = FloatArray(16)
    private val viewMatrix = FloatArray(16)
    private val viewProjectMatrix = FloatArray(16)
    private val modelMatrix = FloatArray(16)
    private val rotateMatrix = FloatArray(16)
    private val currentTime = System.currentTimeMillis()
    private var frames = 0


    private fun progress(): Int {
        val millis = System.currentTimeMillis() - currentTime
        if (millis >= frames * TIME_FRAME) {
            frames++
        }
        return frames
    }

    override fun onDrawFrame(gl: GL10?) {
        glClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT)



        box.enable()
        rotateMatrix(box1, 0.5f, 1f, 0f)
//        Matrix.setIdentityM(rotateMatrix, 0)
//        Matrix.rotateM(rotateMatrix, 0, progress() * 1.5f, 0.5f, 1f, 0f)
//        box.modelMatrix(rotateMatrix)
        box.draw()


        box1.enable()
        rotateMatrix(box1, 0.5f, 0f, 1f)
//        Matrix.setIdentityM(rotateMatrix, 0)
//        Matrix.rotateM(rotateMatrix, 0, progress() * 1.5f, 0.5f, 0f, 1f)
//        box1.modelMatrix(rotateMatrix)
        box1.draw()

        box2.enable()
        rotateMatrix(box2, 0.5f, 1f, 1f)
//        Matrix.setIdentityM(rotateMatrix, 0)
//        Matrix.rotateM(rotateMatrix, 0, progress() * 1.5f, 0.5f, 1f, 1f)
//        box2.modelMatrix(rotateMatrix)
        box2.draw()

        box3.enable()
        rotateMatrix(box3, 0f, 0.8f, 1f)
//        Matrix.setIdentityM(rotateMatrix, 0)
//        Matrix.rotateM(rotateMatrix, 0, progress() * 1.5f, 0f, 0.8f, 1f)
//        box3.modelMatrix(rotateMatrix)
        box3.draw()
    }

    private fun rotateMatrix(box: Box, x: Float, y: Float, z: Float) {
        Matrix.setIdentityM(rotateMatrix, 0)
        Matrix.rotateM(rotateMatrix, 0, progress() * 1.5f, x, y, z)
        box.modelMatrix(rotateMatrix)

    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        glViewport(0, 0, width, height)

        Matrix.perspectiveM(projectMatrix, 0, 45f, width.toFloat() / height, 0.1f, 100f)

        Matrix.setLookAtM(
            viewMatrix, 0,
            0f, 0f, 3f,
            0f, 0f, 0f,
            0f, 1f, 0f
        )

//        Matrix.multiplyMM(viewProjectMatrix, 0, projectMatrix, 0, viewMatrix, 0)
//
//        System.arraycopy(temp, 0, projectMatrix, 0, temp.size)
//        box.setMatrix(projectMatrix)
//        println("矩阵$projectMatrix")

//        box.modelMatrix(translateM(-0.8f, 0f, -8f))
//        box1.modelMatrix(translateM(0.8f, 0f, -8f))
//        box2.modelMatrix(translateM(-0.4f, 1f, -10f))
//        box3.modelMatrix(translateM(0.5f, -0.8f, -5f))

//        box.viewMatrix(viewMatrix)
//        box1.viewMatrix(viewMatrix)
//        box2.viewMatrix(viewMatrix)
//        box3.viewMatrix(viewMatrix)
//
//
//        box.projectionMatrix(projectMatrix)
//        box1.projectionMatrix(projectMatrix)
//        box2.projectionMatrix(projectMatrix)
//        box3.projectionMatrix(projectMatrix)

        viewProjectionMatrix(box)
        viewProjectionMatrix(box1)
        viewProjectionMatrix(box2)
        viewProjectionMatrix(box3)


    }

    private fun viewProjectionMatrix(box: Box) {
        box.viewMatrix(viewMatrix)
        box.projectionMatrix(projectMatrix)
    }


    private fun translateM(x: Float, y: Float, z: Float): FloatArray {
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
        box1 = Box()
        box2 = Box()
        box3 = Box()

    }
}