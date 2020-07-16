package com.hxs.opengles3demo.obj


import android.opengl.GLES20.*
import android.opengl.GLES30
import com.hxs.opengles3demo.R
import com.hxs.opengles3demo.TextureHelper
import java.nio.FloatBuffer


object Model3D : ObjectGL() {

    private val objVAO = IntArray(1)
    private val objEBO = IntArray(1)
    private val objVBO = IntArray(1)


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
//        val boxTexture =
//            TextureHelper.loadTexture(R.drawable.box)
//        val frameTexture =
//            TextureHelper.loadTexture(R.drawable.box_frame)


        glUseProgram(program)
        glUniform3f(lightColorLocation, 1.0f, 1.0f, 1.0f)
        glUniform1i(diffuseLocation, 1)
        glUniform1i(specularLocation, 2)

        glUniform3f(specularLocation, 0.5f, 0.5f, 0.5f)
        glUniform1f(shininessLocation, 32.0f)

        glUniform3f(lightAmbientLocation, 0.2f, 0.2f, 0.2f)
        glUniform3f(lightDiffuseLocation, 0.5f, 0.5f, 0.5f)
        glUniform3f(lightSpecularLocation, 1.0f, 1.0f, 1.0f)

//        glActiveTexture(GL_TEXTURE1)
//        glBindTexture(GL_TEXTURE_2D, boxTexture)
//        glActiveTexture(GL_TEXTURE2)
//        glBindTexture(GL_TEXTURE_2D, frameTexture)


    }


    private fun bindData() {
        GLES30.glGenVertexArrays(objVAO.size, objVAO, 0)
        GLES30.glBindVertexArray(objVAO[0])
//        glVertexAttribPointer(0, 3, )


    }


    private fun bindVBO(vertexBuffer: FloatBuffer, size: Int) {
        glBindBuffer(GL_ARRAY_BUFFER, objVBO[0])
        vertexBuffer.position(0)
        glBufferData(GL_ARRAY_BUFFER, size * FLOAT_BYTE_COUNT, vertexBuffer, GL_STATIC_DRAW)
    }


    private fun bindEBO(indexBuffer: FloatBuffer, size: Int) {
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, objEBO[0])
        indexBuffer.position(0)
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, size * FLOAT_BYTE_COUNT, indexBuffer, GL_STATIC_DRAW)
    }




    override fun enable() {

    }

    override fun draw() {

    }
}