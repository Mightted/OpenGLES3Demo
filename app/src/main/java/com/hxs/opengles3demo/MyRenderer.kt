package com.hxs.opengles3demo

import android.opengl.EGLObjectHandle
import android.opengl.GLES20.*
import android.opengl.GLSurfaceView
import android.opengl.Matrix
import android.os.SystemClock
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin


const val TIME_FRAME: Float = 1000f / 60f

class MyRenderer : GLSurfaceView.Renderer {

    private lateinit var box: Box
    private lateinit var light: Light

    //        private lateinit var box1: Box
//    private lateinit var box2: Box
//    private lateinit var box3: Box
    private val projectMatrix = FloatArray(16)
    private val viewMatrix = FloatArray(16)
    private val modelMatrix = FloatArray(16)
    private val rotateMatrix = FloatArray(16)
    private val tempMatrix = FloatArray(16)
    private var currentTime = System.currentTimeMillis()
    private var frames = 0
    private val defaultRadius = 10f
    private var eyeX: Float = 0f
    private var eyeY: Float = 0f
    private var eyeZ: Float = defaultRadius
    private var xzRadian: Float = 0f
    private var yRadian: Float = 0f
    private var currentRadian: Float = 0f


    private fun progress(): Int {
        val millis = System.currentTimeMillis() - currentTime
        if (millis >= frames * TIME_FRAME) {
            frames++
        }
        return frames
    }

    override fun onDrawFrame(gl: GL10?) {
        glClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT)

        observedCamera(box, 0.5f, 1f, 0f) {
            rotateCamera(it)
            box.viewPos(eyeX, eyeX, eyeZ)
            box.lightPos(light.objPos[0], light.objPos[1], light.objPos[2])

        }


        observedCamera(light, 0.5f, 0f, 1f) {
//            rotateMatrix(it, 0.5f, 0f, 1f, 0f)
            rotateCamera(it)
//            autoCamera(it)
        }
//
//        observedCamera(box2, 0.5f, 1f, 1f)
//
//        observedCamera(box3, 0f, 0.8f, 1f)
    }

    private fun observedCamera(
        objectGL: ObjectGL,
        x: Float,
        y: Float,
        z: Float,
        handle: (ObjectGL) -> Unit
    ) {
        objectGL.enable()
//        rotateMatrix(box, x, y, z)
        handle(objectGL)
//        autoCamera(objectGL)
        objectGL.draw()
    }

    private fun rotateMatrix(objectGL: ObjectGL, angle: Float, x: Float, y: Float, z: Float) {
        Matrix.setIdentityM(tempMatrix, 0)
        Matrix.rotateM(tempMatrix, 0, angle, x, y, z)
        Matrix.multiplyMM(rotateMatrix, 0, tempMatrix, 0, modelMatrix, 0)
        System.arraycopy(rotateMatrix, 0, modelMatrix, 0, 16)
        objectGL.modelMatrix(modelMatrix)

    }

    private fun autoCamera(objectGL: ObjectGL) {
        val radian: Float = PI.toFloat() * progress() * 1.5f / 180

        rotateCamera(
            objectGL,
            sin(-radian) * (cos(currentRadian) * defaultRadius),
            -defaultRadius * sin(currentRadian),
            cos(radian) * (cos(currentRadian) * defaultRadius)
        )
    }

    /**
     * 旋转相机
     */
    private fun rotateCamera(
        objectGL: ObjectGL,
        x: Float = eyeX,
        y: Float = eyeY,
        z: Float = eyeZ
    ) {
//        eyeX = x
//        eyeY = y
//        eyeZ = z
        Matrix.setIdentityM(viewMatrix, 0)
        Matrix.setLookAtM(
            viewMatrix, 0,
            x, y, z,
            0f, 0f, 0f,
            0f, 1f, 0f
        )

        objectGL.viewMatrix(viewMatrix)

    }

    /**
     * 旋转物体
     */
    private fun rotateObject(objectGL: ObjectGL, angle: Float, x: Float, y: Float, z: Float) {
//        Matrix.setIdentityM(modelMatrix, 0)
        Matrix.rotateM(modelMatrix, 0, angle, x, y, z)
        objectGL.modelMatrix(modelMatrix)
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        glViewport(0, 0, width, height)

        Matrix.perspectiveM(projectMatrix, 0, 45f, width.toFloat() / height, 0.1f, 100f)

        Matrix.setLookAtM(
            viewMatrix, 0,
            eyeX, eyeY, eyeZ,
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
        viewProjectionMatrix(light)
//        viewProjectionMatrix(box2)
//        viewProjectionMatrix(box3)


    }

    private fun viewProjectionMatrix(objectGL: ObjectGL) {
        objectGL.viewMatrix(viewMatrix)
        objectGL.projectionMatrix(projectMatrix)
    }


    private fun translateM(x: Float, y: Float, z: Float): FloatArray {
        Matrix.setIdentityM(modelMatrix, 0)
        Matrix.translateM(modelMatrix, 0, x, y, z)
        return modelMatrix
    }

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        glClearColor(0.1f, 0.1f, 0.1f, 1.0f)
        glEnable(GL_DEPTH_TEST)

        light = Light().apply {

            modelMatrix(modelMatrix.apply {
                Matrix.setIdentityM(modelMatrix, 0)
                Matrix.scaleM(modelMatrix, 0, 0.5f, 0.5f, 0.5f)
                Matrix.translateM(modelMatrix, 0, 1.4f, 1f, 2f)
            })
        }
        box = Box().apply {
            //            lightPos(1.4f, 1f, -2f)
            lightPos(light.objPos[0], light.objPos[1], light.objPos[2])
            viewPos(eyeX, eyeX, eyeZ)
        }
//        box2 = Box()
//        box3 = Box()

    }

    fun moveCamera(x: Float, y: Float) {
        val xzAngle = PI.toFloat() * x * 100f / 180 + xzRadian
        var yAngle = PI.toFloat() * y * 100f / 180 + yRadian
        if (yAngle >= 0.5f * PI.toFloat() - 0.02f) {
            yAngle = 0.5f * PI.toFloat() - 0.02f
        } else if (yAngle <= -0.5f * PI.toFloat() + 0.02f) {
            yAngle = -0.5f * PI.toFloat() + 0.02f
        }
//        println(yAngle)
        val xzRadius = cos(yAngle) * defaultRadius
        eyeX = sin(-xzAngle) * xzRadius
        eyeY = -sin(yAngle) * defaultRadius
        eyeZ = cos(xzAngle) * xzRadius

//        currentRadian = yAngle

    }

    fun stopMove(x: Float, y: Float) {
        xzRadian += PI.toFloat() * x * 100f / 180
        yRadian += PI.toFloat() * y * 100f / 180
    }


    fun onResume() {
        currentTime = System.currentTimeMillis() - (frames * TIME_FRAME).toInt()
    }
}