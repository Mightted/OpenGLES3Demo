package com.hxs.opengles3demo

/**
 * Time: 2020/6/28
 * Author: Mightted
 * Description:
 */
interface ObjectGL {

    fun enable()

    fun projectionMatrix(matrix: FloatArray, force: Boolean = false)

    fun modelMatrix(matrix: FloatArray, force: Boolean = false)

    fun viewMatrix(matrix: FloatArray, force: Boolean = false)

    fun draw()
}