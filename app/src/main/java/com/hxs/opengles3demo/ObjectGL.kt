package com.hxs.opengles3demo

import android.opengl.GLES20
import android.opengl.Matrix

/**
 * Time: 2020/6/28
 * Author: Mightted
 * Description:
 */
abstract class ObjectGL {

    var projectionMatrixLocation :Int = 0
    var modelMatrixLocation :Int = 0
    var viewMatrixLocation :Int = 0

     var program:Int = 0


     fun initProgram(vertexId:Int, fragmentId:Int) {
         program = ShaderUtil.getProgram(vertexId, fragmentId)

     }


    protected var modelMatrix = FloatArray(16).apply {
        Matrix.setIdentityM(this, 0)
    }
    protected var viewMatrix = FloatArray(16).apply {
        Matrix.setIdentityM(this, 0)
    }
    protected var projectionMatrix = FloatArray(16).apply {
        Matrix.setIdentityM(this, 0)
    }



    abstract fun enable()


     fun projectionMatrix(matrix: FloatArray, force: Boolean = false) {
        if (matrix.contentEquals(projectionMatrix) && !force) {
            return
        }
        projectionMatrix = matrix.clone()
        GLES20.glUseProgram(program)
        GLES20.glUniformMatrix4fv(projectionMatrixLocation, 1, false, matrix, 0)
    }

     fun modelMatrix(matrix: FloatArray, force: Boolean = false) {
        if (matrix.contentEquals(modelMatrix) && !force) {
            return
        }
        modelMatrix = matrix.clone()
        GLES20.glUseProgram(program)
        GLES20.glUniformMatrix4fv(modelMatrixLocation, 1, false, matrix, 0)
    }

     fun viewMatrix(matrix: FloatArray, force: Boolean = false) {
        if (matrix.contentEquals(viewMatrix) && !force) {
            return
        }
        viewMatrix = matrix.clone()
        GLES20.glUseProgram(program)
        GLES20.glUniformMatrix4fv(viewMatrixLocation, 1, false, matrix, 0)

    }

//    fun projectionMatrix(matrix: FloatArray, force: Boolean = false)
//
//    fun modelMatrix(matrix: FloatArray, force: Boolean = false)
//
//    fun viewMatrix(matrix: FloatArray, force: Boolean = false)

    abstract fun draw()
}