package com.hxs.opengles3demo

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

        val compileStatus = IntArray(1)
        glGetShaderiv(vertexShader, GL_COMPILE_STATUS, compileStatus, 0)
        if (compileStatus[0] == 0) {

            println("编译失败:${glGetShaderInfoLog(vertexShader)}")
        }

        val program = glCreateProgram()
        glAttachShader(program, vertexShader)
        glAttachShader(program, fragmentShader)

        glLinkProgram(program)

        val linkStatus = IntArray(1)
        glGetProgramiv(program, GL_LINK_STATUS, linkStatus, 0)

        if (linkStatus[0] == 0) {
            println("hxs:链接失败")
        }
        glDeleteShader(vertexShader)
        glDeleteShader(fragmentShader)
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