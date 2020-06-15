package com.hxs.opengles3demo

import android.opengl.GLES20.*
import android.opengl.GLES30
import android.opengl.Matrix

import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import java.nio.IntBuffer
import javax.microedition.khronos.opengles.GL

//import android.opengl.GLES30.*

const val FLOAT_BYTE_COUNT = 4
const val COUNT_LOCATION = 3
const val COUNT_COLOR = 3
const val COUNT_TEXTURE = 2

const val STRIDE = (COUNT_LOCATION + COUNT_COLOR + COUNT_TEXTURE) * FLOAT_BYTE_COUNT

class Box {

    private val objVBO = IntArray(1)
    private val objVAO = IntArray(1)
    private val objEBO = IntArray(1)
    private val program = ShaderUtil.getProgram(R.raw.box_vertex_shader, R.raw.box_fragment_shader)

    private var vertices =
        floatArrayOf( //     ---- 位置 ----       ---- 颜色 ----     - 纹理坐标 -
            0.5f, 0.5f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f,  // 右上
            0.5f, -0.5f, 0.0f, 0.0f, 1.0f, 0.0f, 1.0f, 0.0f,  // 右下
            -0.5f, -0.5f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f,  // 左下
            -0.5f, 0.5f, 0.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f // 左上
        )

    private val indices = intArrayOf( // 注意索引从0开始!
        0, 1, 3, // 第一个三角形
        1, 2, 3  // 第二个三角形
    )


    private val vertexBuffer: FloatBuffer =
        ByteBuffer.allocateDirect(vertices.size * FLOAT_BYTE_COUNT)
            .order(ByteOrder.nativeOrder()).asFloatBuffer().put(vertices)

    private val indexBuffer: IntBuffer = ByteBuffer.allocateDirect(indices.size * 4)
        .order(ByteOrder.nativeOrder()).asIntBuffer().put(indices)

    init {
        bindData()
    }

    private fun bindData() {

        glGenBuffers(objVBO.size, objVBO, 0)
        glGenBuffers(objEBO.size, objEBO, 0)

        GLES30.glGenVertexArrays(objVAO.size, objVAO, 0)
        val uMatrixLocation = glGetUniformLocation(program, "uMatrix")

        val matrix = FloatArray(16)

        GLES30.glBindVertexArray(objVAO[0])

        vertexBuffer.position(0)
        glBindBuffer(GL_ARRAY_BUFFER, objVBO[0])
        // 如果没有上面的position(0)就会触发异常 java.lang.IllegalArgumentException: remaining() < size < needed
        glBufferData(
            GL_ARRAY_BUFFER,
            vertices.size * FLOAT_BYTE_COUNT,
            vertexBuffer,
            GL_STATIC_DRAW
        )

        indexBuffer.position(0)
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, objEBO[0])
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices.size * 4, indexBuffer, GL_STATIC_DRAW)


        Matrix.setIdentityM(matrix, 0)
        glUniformMatrix4fv(uMatrixLocation, 1, false, matrix, 0)
        glVertexAttribPointer(0, 3, GL_FLOAT, false, STRIDE, 0)
        glVertexAttribPointer(1, 3, GL_FLOAT,
            false, STRIDE, COUNT_LOCATION * FLOAT_BYTE_COUNT)
        glEnableVertexAttribArray(0)
        glEnableVertexAttribArray(1)

        GLES30.glBindVertexArray(0)


    }

    fun enable() {
        glUseProgram(program)
        GLES30.glBindVertexArray(objVAO[0])
    }

    fun draw() {
        glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 0)
    }


}