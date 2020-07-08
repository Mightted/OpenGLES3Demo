package com.hxs.opengles3demo

import android.opengl.GLES20.*
import android.opengl.GLES30
import android.opengl.Matrix
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import java.nio.IntBuffer


const val FLOAT_BYTE_COUNT = 4
const val COUNT_LOCATION = 3
const val COUNT_NORMAL = 3
const val COUNT_COLOR = 3
const val COUNT_TEXTURE = 2

const val STRIDE = (COUNT_LOCATION + COUNT_NORMAL) * FLOAT_BYTE_COUNT

class Box : ObjectGL() {

    private val objVAO = IntArray(1)

    private val objEBO = IntArray(1)


    private fun initUniformLocation() {
        projectionMatrixLocation = glGetUniformLocation(program, "projectionMatrix")
        modelMatrixLocation = glGetUniformLocation(program, "modelMatrix")
        viewMatrixLocation = glGetUniformLocation(program, "viewMatrix")
        lightColorLocation = glGetUniformLocation(program, "lightColor")
//        objectColorLocation = glGetUniformLocation(program, "objectColor")
        lightPosLocation = glGetUniformLocation(program, "lightPos")
        viewPosLocation = glGetUniformLocation(program, "viewPos")


    }

    private var lightColorLocation = 0
//    private var objectColorLocation = 0
    private var lightPosLocation = 0
    private var viewPosLocation = 0
    private var ambient  = glGetUniformLocation(program, "material.ambient")


    init {

        initProgram(R.raw.box_vertex_shader, R.raw.box_fragment_shader)
        initUniformLocation()
        bindData()
        glUseProgram(program)

        modelMatrix(modelMatrix, true)
        viewMatrix(viewMatrix, true)
        projectionMatrix(projectionMatrix, true)


//        glUniform3f(objectColorLocation, 1.0f, 0.5f, 0.31f)
        glUniform3f(lightColorLocation, 1.0f, 1.0f, 1.0f)
    }


    private fun bindData() {
        GLES30.glGenVertexArrays(objVAO.size, objVAO, 0)

        GLES30.glBindVertexArray(objVAO[0])

        VBOHelper.updateVBO()


        glVertexAttribPointer(0, 3, GL_FLOAT, false, STRIDE, 0)
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

        GLES30.glBindVertexArray(0)


    }

    override fun enable() {
        glUseProgram(program)
        GLES30.glBindVertexArray(objVAO[0])
    }

    fun lightPos(x: Float, y: Float, z: Float) {
        glUseProgram(program)
        glUniform3f(lightPosLocation, x, y, z)
    }

    fun viewPos(x: Float, y: Float, z: Float) {
        glUseProgram(program)
        glUniform3f(viewPosLocation, x, y, z)
    }

    override fun draw() {
        glDrawArrays(GL_TRIANGLES, 0, 36)
    }


}