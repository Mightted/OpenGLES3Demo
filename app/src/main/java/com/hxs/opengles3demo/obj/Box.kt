package com.hxs.opengles3demo.obj

import android.opengl.GLES20.*
import android.opengl.GLES30
import com.hxs.opengles3demo.R
import com.hxs.opengles3demo.TextureHelper
import com.hxs.opengles3demo.VBOHelper

const val FLOAT_BYTE_COUNT = 4
const val COUNT_LOCATION = 3
const val COUNT_NORMAL = 3
const val COUNT_COLOR = 3
const val COUNT_TEXTURE = 2

const val STRIDE = (COUNT_LOCATION + COUNT_NORMAL + COUNT_TEXTURE) * FLOAT_BYTE_COUNT

class Box : ObjectGL() {

    private val objVAO = IntArray(1)

    private val objEBO = IntArray(1)


    private fun initUniformLocation() {
        projectionMatrixLocation = glGetUniformLocation(program, "projectionMatrix")
        modelMatrixLocation = glGetUniformLocation(program, "modelMatrix")
        viewMatrixLocation = glGetUniformLocation(program, "viewMatrix")
        lightColorLocation = glGetUniformLocation(program, "lightColor")
//        objectColorLocation = glGetUniformLocation(program, "objectColor")
        viewPosLocation = glGetUniformLocation(program, "viewPos")

//        ambientLocation = glGetUniformLocation(program, "material.ambient")
        diffuseLocation = glGetUniformLocation(program, "material.diffuse")
        specularLocation = glGetUniformLocation(program, "material.specular")
        shininessLocation = glGetUniformLocation(program, "material.shininess")

        lightPosLocation = glGetUniformLocation(program, "light.position")
        lightAmbientLocation = glGetUniformLocation(program, "light.ambient")
        lightDiffuseLocation = glGetUniformLocation(program, "light.diffuse")
        lightSpecularLocation = glGetUniformLocation(program, "light.specular")


    }

    private var lightColorLocation = 0

    private var viewPosLocation = 0

    private var diffuseLocation = 0
    private var specularLocation = 0
    private var shininessLocation = 0

    private var lightAmbientLocation = 0
    private var lightDiffuseLocation = 0
    private var lightSpecularLocation = 0
    private var lightPosLocation = 0


    init {

        initProgram(
            R.raw.box_vertex_shader,
            R.raw.box_fragment_shader
        )
        initUniformLocation()
        bindData()
        initMatrix()
         val boxTexture =
             TextureHelper.loadTexture(R.drawable.box)
        val frameTexture =
            TextureHelper.loadTexture(R.drawable.box_frame)


//        glUniform3f(objectColorLocation, 1.0f, 0.5f, 0.31f)
        glUseProgram(program)
        glUniform3f(lightColorLocation, 1.0f, 1.0f, 1.0f)
//        glUniform3f(ambientLocation, 1.0f, 0.5f, 0.31f)
        glUniform1i(diffuseLocation, 1)
        glUniform1i(specularLocation, 2)

        glUniform3f(specularLocation, 0.5f, 0.5f, 0.5f)
        glUniform1f(shininessLocation, 32.0f)

        glUniform3f(lightAmbientLocation, 0.2f, 0.2f, 0.2f)
        glUniform3f(lightDiffuseLocation, 0.5f, 0.5f, 0.5f)
        glUniform3f(lightSpecularLocation, 1.0f, 1.0f, 1.0f)

        glActiveTexture(GL_TEXTURE1)
        glBindTexture(GL_TEXTURE_2D, boxTexture)
        glActiveTexture(GL_TEXTURE2)
        glBindTexture(GL_TEXTURE_2D, frameTexture)


    }


    private fun bindData() {
        GLES30.glGenVertexArrays(objVAO.size, objVAO, 0)

        GLES30.glBindVertexArray(objVAO[0])

        VBOHelper.updateVBO()


        glVertexAttribPointer(0, 3, GL_FLOAT, false,
            STRIDE, 0)
        glVertexAttribPointer(
            1,
            3,
            GL_FLOAT,
            false,
            STRIDE,
            (COUNT_LOCATION) * FLOAT_BYTE_COUNT
        )
        glVertexAttribPointer(
            2,
            2,
            GL_FLOAT,
            false,
            STRIDE,
            (COUNT_LOCATION + COUNT_NORMAL) * FLOAT_BYTE_COUNT
        )


        glEnableVertexAttribArray(0)
        glEnableVertexAttribArray(1)
        glEnableVertexAttribArray(2)

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