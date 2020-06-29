package com.hxs.opengles3demo

import android.opengl.GLES20.*
import android.opengl.GLES30
import android.opengl.Matrix
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import java.nio.IntBuffer

//import android.opengl.GLES30.*

const val FLOAT_BYTE_COUNT = 4
const val COUNT_LOCATION = 3
const val COUNT_NORMAL = 3
const val COUNT_COLOR = 3
const val COUNT_TEXTURE = 2

const val STRIDE = (COUNT_LOCATION + COUNT_NORMAL) * FLOAT_BYTE_COUNT

class Box : ObjectGL {

    //    private val objVBO = IntArray(1)
    private val objVAO = IntArray(1)

    private val objEBO = IntArray(1)
    private val program = ShaderUtil.getProgram(R.raw.box_vertex_shader, R.raw.box_fragment_shader)


    private var vertices = floatArrayOf(
        -0.5f, -0.5f, -0.5f, 0.0f, 0.0f, -1.0f,
        0.5f, -0.5f, -0.5f, 0.0f, 0.0f, -1.0f,
        0.5f, 0.5f, -0.5f, 0.0f, 0.0f, -1.0f,
        0.5f, 0.5f, -0.5f, 0.0f, 0.0f, -1.0f,
        -0.5f, 0.5f, -0.5f, 0.0f, 0.0f, -1.0f,
        -0.5f, -0.5f, -0.5f, 0.0f, 0.0f, -1.0f,

        -0.5f, -0.5f, 0.5f, 0.0f, 0.0f, 1.0f,
        0.5f, -0.5f, 0.5f, 0.0f, 0.0f, 1.0f,
        0.5f, 0.5f, 0.5f, 0.0f, 0.0f, 1.0f,
        0.5f, 0.5f, 0.5f, 0.0f, 0.0f, 1.0f,
        -0.5f, 0.5f, 0.5f, 0.0f, 0.0f, 1.0f,
        -0.5f, -0.5f, 0.5f, 0.0f, 0.0f, 1.0f,

        -0.5f, 0.5f, 0.5f, -1.0f, 0.0f, 0.0f,
        -0.5f, 0.5f, -0.5f, -1.0f, 0.0f, 0.0f,
        -0.5f, -0.5f, -0.5f, -1.0f, 0.0f, 0.0f,
        -0.5f, -0.5f, -0.5f, -1.0f, 0.0f, 0.0f,
        -0.5f, -0.5f, 0.5f, -1.0f, 0.0f, 0.0f,
        -0.5f, 0.5f, 0.5f, -1.0f, 0.0f, 0.0f,

        0.5f, 0.5f, 0.5f, 1.0f, 0.0f, 0.0f,
        0.5f, 0.5f, -0.5f, 1.0f, 0.0f, 0.0f,
        0.5f, -0.5f, -0.5f, 1.0f, 0.0f, 0.0f,
        0.5f, -0.5f, -0.5f, 1.0f, 0.0f, 0.0f,
        0.5f, -0.5f, 0.5f, 1.0f, 0.0f, 0.0f,
        0.5f, 0.5f, 0.5f, 1.0f, 0.0f, 0.0f,

        -0.5f, -0.5f, -0.5f, 0.0f, -1.0f, 0.0f,
        0.5f, -0.5f, -0.5f, 0.0f, -1.0f, 0.0f,
        0.5f, -0.5f, 0.5f, 0.0f, -1.0f, 0.0f,
        0.5f, -0.5f, 0.5f, 0.0f, -1.0f, 0.0f,
        -0.5f, -0.5f, 0.5f, 0.0f, -1.0f, 0.0f,
        -0.5f, -0.5f, -0.5f, 0.0f, -1.0f, 0.0f,

        -0.5f, 0.5f, -0.5f, 0.0f, 1.0f, 0.0f,
        0.5f, 0.5f, -0.5f, 0.0f, 1.0f, 0.0f,
        0.5f, 0.5f, 0.5f, 0.0f, 1.0f, 0.0f,
        0.5f, 0.5f, 0.5f, 0.0f, 1.0f, 0.0f,
        -0.5f, 0.5f, 0.5f, 0.0f, 1.0f, 0.0f,
        -0.5f, 0.5f, -0.5f, 0.0f, 1.0f, 0.0f
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

    private val projectionMatrixLocation = glGetUniformLocation(program, "projectionMatrix")
    private val modelMatrixLocation = glGetUniformLocation(program, "modelMatrix")
    private val viewMatrixLocation = glGetUniformLocation(program, "viewMatrix")
    private val lightColorLocation = glGetUniformLocation(program, "lightColor")
    private val objectColorLocation = glGetUniformLocation(program, "objectColor")
    private val lightPosLocation = glGetUniformLocation(program, "lightPos")
//    private val uTextureUnit1 = glGetUniformLocation(program, "uTextureUnit1")
//    private val uTextureUnit2 = glGetUniformLocation(program, "uTextureUnit2")


//    private val vertexBuffer: FloatBuffer =
//        ByteBuffer.allocateDirect(vertices.size * FLOAT_BYTE_COUNT)
//            .order(ByteOrder.nativeOrder()).asFloatBuffer().put(vertices)

//    private val indexBuffer: IntBuffer = ByteBuffer.allocateDirect(indices.size * 4)
//        .order(ByteOrder.nativeOrder()).asIntBuffer().put(indices)


    init {

        bindData()
        glUseProgram(program)
//        val texture1 = TextureHelper.loadTexture(R.drawable.container)
//        val texture2 = TextureHelper.loadTexture(R.drawable.awesomeface, true)

        modelMatrix(modelMatrix, true)
        viewMatrix(viewMatrix, true)
        projectionMatrix(projectionMatrix, true)

        glUniform3f(objectColorLocation, 1.0f, 0.5f, 0.31f)
        glUniform3f(lightColorLocation, 1.0f, 1.0f, 1.0f)


//        glActiveTexture(GL_TEXTURE0)
//        glBindTexture(GL_TEXTURE_2D, texture1)
//        glActiveTexture(GL_TEXTURE1)
//        glBindTexture(GL_TEXTURE_2D, texture2)
//        glUniform1i(uTextureUnit1, 0)
//        glUniform1i(uTextureUnit2, 1)
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

//        indexBuffer.position(0)
//        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, objEBO[0])
//        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices.size * 4, indexBuffer, GL_STATIC_DRAW)


        glVertexAttribPointer(0, 3, GL_FLOAT, false, STRIDE, 0)
//        glVertexAttribPointer(
//            1, 3, GL_FLOAT,
//            false, STRIDE, COUNT_LOCATION * FLOAT_BYTE_COUNT
//        )
        glVertexAttribPointer(
            1,
            3,
            GL_FLOAT,
            false,
            STRIDE,
            (COUNT_LOCATION) * FLOAT_BYTE_COUNT
        )
        glEnableVertexAttribArray(0)
        glEnableVertexAttribArray(1)
//        glEnableVertexAttribArray(2)

        GLES30.glBindVertexArray(0)


    }

    override fun enable() {
        glUseProgram(program)
//        bindData(matrix)
//        glBindTexture(GL_TEXTURE_2D, texture)

        GLES30.glBindVertexArray(objVAO[0])
    }

    override fun projectionMatrix(matrix: FloatArray, force: Boolean) {
        if (matrix.contentEquals(projectionMatrix) && !force) {
            return
        }
        projectionMatrix = matrix.clone()
        glUseProgram(program)
        glUniformMatrix4fv(projectionMatrixLocation, 1, false, matrix, 0)
    }

    override fun modelMatrix(matrix: FloatArray, force: Boolean) {
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

    fun lightPos(x: Float, y: Float, z: Float) {
        glUseProgram(program)
        glUniform3f(lightColorLocation, x, y, z)
    }

    override fun draw() {
        glDrawArrays(GL_TRIANGLES, 0, 36)
    }


}