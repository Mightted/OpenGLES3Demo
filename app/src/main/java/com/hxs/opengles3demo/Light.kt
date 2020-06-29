package com.hxs.opengles3demo

import android.opengl.GLES20.*
import android.opengl.GLES30
import android.opengl.Matrix
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer

/**
 * Time: 2020/6/28
 * Author: Mightted
 * Description:
 */
class Light:ObjectGL {

//    private val objVBO = IntArray(1)
    private val objVAO = IntArray(1)

    //    private val objEBO = IntArray(1)
    private val program = ShaderUtil.getProgram(R.raw.light_vertex_shader, R.raw.lignt_fragment_shader)


    private var vertices = floatArrayOf(
        -0.5f, -0.5f, -0.5f,
        0.5f, -0.5f, -0.5f,
        0.5f, 0.5f, -0.5f,
        0.5f, 0.5f, -0.5f,
        -0.5f, 0.5f, -0.5f,
        -0.5f, -0.5f, -0.5f,
        -0.5f, -0.5f, 0.5f,
        0.5f, -0.5f, 0.5f,
        0.5f, 0.5f, 0.5f,
        0.5f, 0.5f, 0.5f,
        -0.5f, 0.5f, 0.5f,
        -0.5f, -0.5f, 0.5f,
        -0.5f, 0.5f, 0.5f,
        -0.5f, 0.5f, -0.5f,
        -0.5f, -0.5f, -0.5f,
        -0.5f, -0.5f, -0.5f,
        -0.5f, -0.5f, 0.5f,
        -0.5f, 0.5f, 0.5f,
        0.5f, 0.5f, 0.5f,
        0.5f, 0.5f, -0.5f,
        0.5f, -0.5f, -0.5f,
        0.5f, -0.5f, -0.5f,
        0.5f, -0.5f, 0.5f,
        0.5f, 0.5f, 0.5f,
        -0.5f, -0.5f, -0.5f,
        0.5f, -0.5f, -0.5f,
        0.5f, -0.5f, 0.5f,
        0.5f, -0.5f, 0.5f,
        -0.5f, -0.5f, 0.5f,
        -0.5f, -0.5f, -0.5f,
        -0.5f, 0.5f, -0.5f,
        0.5f, 0.5f, -0.5f,
        0.5f, 0.5f, 0.5f,
        0.5f, 0.5f, 0.5f,
        -0.5f, 0.5f, 0.5f,
        -0.5f, 0.5f, -0.5f
    )

    private var modelMatrix = FloatArray(16).apply {
        Matrix.setIdentityM(this, 0)
    }
    private var viewMatrix = FloatArray(16).apply {
        Matrix.setIdentityM(this, 0)
    }
    private var projectionMatrix = FloatArray(16).apply {
        Matrix.setIdentityM(this, 0)
    }

    private val projectionMatrixLocation =glGetUniformLocation(program, "projectionMatrix")
    private val modelMatrixLocation = glGetUniformLocation(program, "modelMatrix")
    private val viewMatrixLocation = glGetUniformLocation(program, "viewMatrix")


    private val vertexBuffer: FloatBuffer =
        ByteBuffer.allocateDirect(vertices.size * FLOAT_BYTE_COUNT)
            .order(ByteOrder.nativeOrder()).asFloatBuffer().put(vertices)



    init {

        bindData()
        glUseProgram(program)

        modelMatrix(modelMatrix, true)
        viewMatrix(viewMatrix, true)
        projectionMatrix(projectionMatrix, true)

    }


    private fun bindData() {
//        glGenBuffers(objVBO.size, objVBO, 0)
//        glGenBuffers(objEBO.size, objEBO, 0)

        GLES30.glGenVertexArrays(objVAO.size, objVAO, 0)

        GLES30.glBindVertexArray(objVAO[0])

        VBOHelper.updateVBO()

//        vertexBuffer.position(0)
//        glBindBuffer(GL_ARRAY_BUFFER, objVBO[0])
//        // 如果没有上面的position(0)就会触发异常 java.lang.IllegalArgumentException: remaining() < size < needed
//        glBufferData(
//            GL_ARRAY_BUFFER,
//            vertices.size * FLOAT_BYTE_COUNT,
//            vertexBuffer,
//            GL_STATIC_DRAW
//        )


        glVertexAttribPointer(0, 3, GL_FLOAT, false, STRIDE, 0)

        glEnableVertexAttribArray(0)

        GLES30.glBindVertexArray(0)


    }

    override fun enable() {
        glUseProgram(program)

        GLES30.glBindVertexArray(objVAO[0])
    }

    override fun projectionMatrix(matrix: FloatArray, force: Boolean ) {
        if (matrix.contentEquals(projectionMatrix) && !force) {
            return
        }
        projectionMatrix = matrix.clone()
        glUseProgram(program)
        glUniformMatrix4fv(projectionMatrixLocation, 1, false, matrix, 0)
    }

    override fun modelMatrix(matrix: FloatArray, force: Boolean ) {
        if (matrix.contentEquals(modelMatrix) && !force) {
            return
        }
        modelMatrix = matrix.clone()
        glUseProgram(program)
        glUniformMatrix4fv(modelMatrixLocation, 1, false, matrix, 0)
    }

    override fun viewMatrix(matrix: FloatArray, force: Boolean) {
        if (matrix.contentEquals(viewMatrix) && !force) {
            return
        }
        viewMatrix = matrix.clone()
        glUseProgram(program)
        glUniformMatrix4fv(viewMatrixLocation, 1, false, matrix, 0)

    }

    override fun draw() {
        glDrawArrays(GL_TRIANGLES, 0, 36)
    }


}