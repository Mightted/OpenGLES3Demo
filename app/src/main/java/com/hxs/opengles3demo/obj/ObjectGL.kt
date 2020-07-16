package com.hxs.opengles3demo.obj

import android.opengl.GLES20
import android.opengl.Matrix
import com.hxs.opengles3demo.ShaderUtil

/**
 * Time: 2020/6/28
 * Author: Mightted
 * Description:
 */
abstract class ObjectGL {

    var projectionMatrixLocation: Int = 0
    var modelMatrixLocation: Int = 0
    var viewMatrixLocation: Int = 0

    var program: Int = 0

    fun initProgram(vertexId: Int, fragmentId: Int) {
        program =
            ShaderUtil.getProgram(vertexId, fragmentId)
    }


    private val modelMatrix = FloatArray(16).apply {
        Matrix.setIdentityM(this, 0)
    }
    private val viewMatrix = FloatArray(16).apply {
        Matrix.setIdentityM(this, 0)
    }
    private val projectionMatrix = FloatArray(16).apply {
        Matrix.setIdentityM(this, 0)
    }

    val objPos: FloatArray = floatArrayOf(0f, 0f, 0f, 1f)
        get() {
            Matrix.multiplyMV(field, 0, modelMatrix, 0, floatArrayOf(0f, 0f, 0f, 1f), 0)
            return field
        }


    abstract fun enable()

    protected fun initMatrix() {
        projectionMatrix()
        viewMatrix()
        modelMatrix()
    }


    fun projectionMatrix(handle: (matrix: FloatArray) -> Unit = {}) {
        handle(projectionMatrix)
        GLES20.glUseProgram(program)
        GLES20.glUniformMatrix4fv(projectionMatrixLocation, 1, false, projectionMatrix, 0)
    }

    fun modelMatrix(handle: (matrix: FloatArray) -> Unit = {}) {
        handle(modelMatrix)
        GLES20.glUseProgram(program)
        GLES20.glUniformMatrix4fv(modelMatrixLocation, 1, false, modelMatrix, 0)
    }

    fun viewMatrix(handle: (matrix: FloatArray) -> Unit = {}) {
        handle(viewMatrix)
        GLES20.glUseProgram(program)
        GLES20.glUniformMatrix4fv(viewMatrixLocation, 1, false, viewMatrix, 0)
    }


    abstract fun draw()
}