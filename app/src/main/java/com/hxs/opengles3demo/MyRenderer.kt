package com.hxs.opengles3demo

import android.opengl.GLES20.*
import android.opengl.GLSurfaceView
import android.opengl.Matrix
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin


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
    private var currentTime = System.currentTimeMillis()
    private var frames = 0
    private val defaultRadius = 8f
    private var eyeX: Float = 0f
    private var eyeY: Float = 0f
    private var eyeZ: Float = defaultRadius
    private var xzRadian: Float = 0f
    private var yRadian: Float = 0f


    private fun progress(): Int {
        val millis = System.currentTimeMillis() - currentTime
        if (millis >= frames * TIME_FRAME) {
            frames++
        }
        return frames
    }

    override fun onDrawFrame(gl: GL10?) {
        glClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT)

        observedCamera(box, 0.5f, 1f, 0f)

        observedCamera(box1, 0.5f, 0f, 1f)
//
//        observedCamera(box2, 0.5f, 1f, 1f)
//
//        observedCamera(box3, 0f, 0.8f, 1f)
    }

    private fun observedCamera(box: Box, x: Float, y: Float, z: Float) {
        box.enable()
//        rotateMatrix(box, x, y, z)
        rotateCamera(box)
        box.draw()
    }

    private fun rotateMatrix(box: Box, x: Float, y: Float, z: Float) {
        Matrix.setIdentityM(rotateMatrix, 0)
        Matrix.rotateM(rotateMatrix, 0, 0 * 1.5f, x, y, z)
        box.modelMatrix(rotateMatrix)

    }

    private fun autoCamera(box: Box) {
        val radian: Float = PI.toFloat() * progress() * 1.5f / 180
        rotateCamera(box, sin(-radian) * 5f, 0f, cos(radian) * 5f)
    }

    private fun rotateCamera(box: Box, x: Float = eyeX, y: Float = eyeY, z: Float = eyeZ) {
        eyeX = x
        eyeY = y
        eyeZ = z
        Matrix.setIdentityM(viewMatrix, 0)
        Matrix.setLookAtM(
            viewMatrix, 0,
            eyeX, eyeY, eyeZ,
            0f, 0f, 0f,
            0f, 1f, 0f
        )

        box.viewMatrix(viewMatrix)

    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        glViewport(0, 0, width, height)

        Matrix.perspectiveM(projectMatrix, 0, 45f, width.toFloat() / height, 0.1f, 100f)

        Matrix.setLookAtM(
            viewMatrix, 0,
            0f, 0f, 5f,
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
        Matrix.setIdentityM(modelMatrix, 0)
        Matrix.translateM(modelMatrix, 0, x, y, z)
        return modelMatrix
    }

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        glClearColor(0.2f, 0.3f, 0.3f, 1.0f)
        glEnable(GL_DEPTH_TEST)
        box = Box().apply {
            modelMatrix(translateM(0.5f, 0f, 1f))
        }
        box1 = Box()
        box2 = Box()
        box3 = Box()

    }

    fun moveCamera(x: Float, y: Float) {
        val xzAngle = PI.toFloat() * x * 100f / 180 + xzRadian
        var yAngle = PI.toFloat() * y * 100f / 180 + yRadian
        if (yAngle >= 0.5f * PI.toFloat()- 0.3f) {
            yAngle = 0.5f * PI.toFloat() - 0.3f
        } else if (yAngle <= -0.5f * PI.toFloat()- 0.3f) {
            yAngle = -0.5f * PI.toFloat() + 0.3f
        }
        println(yAngle)
        val xzRadius = cos(yAngle) * defaultRadius
        eyeX = sin(-xzAngle) * xzRadius
        eyeY = -sin(yAngle) * defaultRadius
        eyeZ = cos(xzAngle) * xzRadius

    }

    fun stopMove(x: Float, y: Float) {
        xzRadian += PI.toFloat() * x * 100f / 180
        yRadian += PI.toFloat() * y * 100f / 180
    }


    fun onResume() {
        currentTime = System.currentTimeMillis() - (frames * TIME_FRAME).toInt()
    }
}