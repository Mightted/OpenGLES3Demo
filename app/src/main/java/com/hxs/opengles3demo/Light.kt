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
class Light : ObjectGL() {

    private val objVAO = IntArray(1)
    private fun initUniformLocation() {
        projectionMatrixLocation = glGetUniformLocation(program, "projectionMatrix")
        modelMatrixLocation = glGetUniformLocation(program, "modelMatrix")
        viewMatrixLocation = glGetUniformLocation(program, "viewMatrix")

    }

    init {

        initProgram(R.raw.light_vertex_shader, R.raw.light_fragment_shader)
        initUniformLocation()
        bindData()
        glUseProgram(program)

        initMatrix()

    }


    private fun bindData() {

        GLES30.glGenVertexArrays(objVAO.size, objVAO, 0)

        GLES30.glBindVertexArray(objVAO[0])

        VBOHelper.updateVBO()



        glVertexAttribPointer(0, 3, GL_FLOAT, false, STRIDE, 0)

        glEnableVertexAttribArray(0)

        GLES30.glBindVertexArray(0)


    }

    override fun enable() {
        glUseProgram(program)

        GLES30.glBindVertexArray(objVAO[0])
    }

    override fun draw() {
        glDrawArrays(GL_TRIANGLES, 0, 36)
    }


}