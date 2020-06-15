package com.hxs.opengles3demo

import android.content.res.Resources
import android.opengl.GLES20.*
//import android.opengl.GLES30.*

object ShaderUtil {


    fun getProgram(vertexId:Int, fragmentId:Int):Int {
        val vertexShader = glCreateShader(GL_VERTEX_SHADER)
        val fragmentShader = glCreateShader(GL_FRAGMENT_SHADER)

        glShaderSource(vertexShader, loadShaderResource(vertexId))
        glShaderSource(fragmentShader, loadShaderResource(fragmentId))

        glCompileShader(vertexShader)
        glCompileShader(fragmentShader)

        val program = glCreateProgram()
        glAttachShader(program, vertexShader)
        glAttachShader(program, fragmentShader)

        glLinkProgram(program)
        return program


    }

    private fun loadShaderResource(resId: Int): String {
        App.resource().openRawResource(resId).use {
            val byteArray = ByteArray(it.available())
            it.read(byteArray)
            return String(byteArray)
        }
    }
}