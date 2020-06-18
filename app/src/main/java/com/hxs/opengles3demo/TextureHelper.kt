package com.hxs.opengles3demo

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.opengl.GLES20.*
import android.opengl.GLUtils
import android.text.TextUtils

/**
 * Time: 2020/6/17
 * Author: Mightted
 * Description:
 */
object TextureHelper {

    fun loadTexture(resId:Int, overturn :Boolean= false) :Int {
        val textures = IntArray(1)
        glGenTextures(1, textures, 0)

        glBindTexture(GL_TEXTURE_2D, textures[0])

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR)
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR_MIPMAP_LINEAR)

        val option = BitmapFactory.Options()
        option.inScaled = false

        val bitmap = BitmapFactory.decodeResource(App.resource(), resId, option).run {
            if (overturn) {
                val matrix = Matrix()
                matrix.setScale(1f, -1f)
                Bitmap.createBitmap(this, 0, 0, width, height, matrix, true).also {
                    this.recycle()
                }
            } else {
                this
            }
        }

        GLUtils.texImage2D(GL_TEXTURE_2D, 0, bitmap, 0)
        bitmap.recycle()
        glGenerateMipmap(GL_TEXTURE_2D)

        glBindTexture(GL_TEXTURE_2D, 0)

        return textures[0]
    }
}