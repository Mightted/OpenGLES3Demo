package com.hxs.opengles3demo

import android.opengl.GLES20.*
import android.opengl.GLSurfaceView
import android.opengl.Matrix
import com.hxs.opengles3demo.obj.Box
import com.hxs.opengles3demo.obj.Light
import com.hxs.opengles3demo.obj.ObjectGL
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10
import kotlin.math.PI


const val TIME_FRAME: Float = 1000f / 60f

class MyRenderer : GLSurfaceView.Renderer {

    private lateinit var box: Box
    private lateinit var light: Light
    private val projectMatrix = FloatArray(16)
    private val viewMatrix = FloatArray(16)
    private val tempMatrix = FloatArray(32)
    private var currentTime = System.currentTimeMillis()
    private var frames = 0
    private val defaultRadius = 6f


    private val positionInstance = PositionInstance()


    private fun progress(): Int {
        val millis = System.currentTimeMillis() - currentTime
        if (millis >= frames * TIME_FRAME) {
            frames++
        }
        return frames
    }

    override fun onDrawFrame(gl: GL10?) {
        glClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT)

        observedCamera(box) {
            it.modelMatrix { matrix ->
                Matrix.setIdentityM(matrix, 0)
                Matrix.rotateM(matrix, 0, -positionInstance.yAngle  / PI.toFloat() * 180f, 1f, 0f,0f)
                Matrix.rotateM(matrix, 0, positionInstance.xzAngle  / PI.toFloat() * 180f, 0f, 1f,0f)
            }

//            positionInstance.getVectorByRadius(defaultRadius) { x, y, z ->
//                box.viewPos(x, y, z)
//                rotateCamera(it, x, y, z)
//            }
            box.lightPos(light.objPos[0], light.objPos[1], light.objPos[2])

        }


        observedCamera(light) {
//            rotateMatrix(it, 0.5f, 0f, 1f, 0f)  // 公转
//            rotateMatrix(it, 0.5f, 0f, 1f, 0f)
            // 摄像头转动
//            positionInstance.getVectorByRadius(defaultRadius) { x, y, z ->
//                rotateCamera(it, x, y, z)
//            }
        }

    }

    private fun observedCamera(objectGL: ObjectGL, handle: (ObjectGL) -> Unit) {
        objectGL.enable()
        handle(objectGL)
        objectGL.draw()
    }

    private fun rotateMatrix(objectGL: ObjectGL, angle: Float, x: Float, y: Float, z: Float) {
        objectGL.modelMatrix {
            Matrix.setIdentityM(tempMatrix, 0)
            Matrix.rotateM(tempMatrix, 0, angle, x, y, z)
            Matrix.multiplyMM(tempMatrix, 16, tempMatrix, 0, it, 0)
            System.arraycopy(tempMatrix, 16, it, 0, 16)
//            Matrix.setIdentityM(it, 0)
//            Matrix.scaleM(it, 0, 0.5f, 0.5f, 0.5f)
//            Matrix.rotateM(it, 0, angle * progress(), x, y, z)
//            Matrix.translateM(it, 0, 0f, 0f, 3f) // 前面
        }


    }

    private fun autoCamera(objectGL: ObjectGL) {
        val radian: Float = PI.toFloat() * progress() * 1.5f / 180


//        rotateCamera(
//            objectGL,
//            sin(-radian) * (cos(currentRadian) * defaultRadius),
//            -defaultRadius * sin(currentRadian),
//            cos(radian) * (cos(currentRadian) * defaultRadius)
//        )
    }

    /**
     * 旋转相机
     */
    private fun rotateCamera(
        objectGL: ObjectGL,
        x: Float,
        y: Float,
        z: Float
    ) {

        objectGL.viewMatrix {
            Matrix.setIdentityM(it, 0)
            Matrix.setLookAtM(
                it, 0,
                x, y, z,
                0f, 0f, 0f,
                0f, 1f, 0f
            )
        }

    }

    /**
     * 旋转物体
     */
//    private fun rotateObject(objectGL: ObjectGL, angle: Float, x: Float, y: Float, z: Float) {
////        Matrix.setIdentityM(modelMatrix, 0)
//        Matrix.rotateM(modelMatrix, 0, angle, x, y, z)
//        objectGL.modelMatrix(modelMatrix)
//    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        glViewport(0, 0, width, height)

        Matrix.perspectiveM(projectMatrix, 0, 45f, width.toFloat() / height, 0.1f, 100f)

        positionInstance.getVectorByRadius(defaultRadius) { x, y, z ->
            Matrix.setLookAtM(
                viewMatrix, 0,
                x, y, z,
                0f, 0f, 0f,
                0f, 1f, 0f
            )
            box.viewPos(x, y, z)
        }


        viewProjectionMatrix(box)
        viewProjectionMatrix(light)


    }

    private fun viewProjectionMatrix(objectGL: ObjectGL) {
        objectGL.viewMatrix {
            System.arraycopy(viewMatrix, 0, it, 0, viewMatrix.size)
        }
        objectGL.projectionMatrix {
            System.arraycopy(projectMatrix, 0, it, 0, projectMatrix.size)
        }
    }


//    private fun translateM(x: Float, y: Float, z: Float): FloatArray {
//        Matrix.setIdentityM(modelMatrix, 0)
//        Matrix.translateM(modelMatrix, 0, x, y, z)
//        return modelMatrix
//    }

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        glClearColor(0.1f, 0.1f, 0.1f, 1.0f)
        glEnable(GL_DEPTH_TEST)

        light = Light().apply {
            modelMatrix {
                Matrix.setIdentityM(it, 0)
                Matrix.scaleM(it, 0, 0.3f, 0.3f, 0.3f)
//                Matrix.translateM(modelMatrix, 0, 3f, 0f, 0f) // 左边
//                Matrix.translateM(modelMatrix, 0, -3f, 0f, 0f) // 右边
//                Matrix.translateM(modelMatrix, 0, 0f, 3f, 0f) // 上面
//                Matrix.translateM(modelMatrix, 0, 0f, -3f, 0f) // 下面
                Matrix.translateM(it, 0, 4f, 4f, 6f) // 前面
//                Matrix.translateM(modelMatrix, 0, 0f, 0f, -3f) // 后面
            }
        }
        box = Box().apply {
            lightPos(light.objPos[0], light.objPos[1], light.objPos[2])
        }

    }

    fun moveCamera(x: Float, y: Float) {
        positionInstance.moveCamera(x, y)

    }

    fun stopMove(x: Float, y: Float) {
        positionInstance.stopMove(x, y)
    }


    fun onResume() {
        currentTime = System.currentTimeMillis() - (frames * TIME_FRAME).toInt()
    }
}