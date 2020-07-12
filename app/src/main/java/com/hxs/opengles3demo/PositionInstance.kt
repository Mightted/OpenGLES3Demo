package com.hxs.opengles3demo

import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

/**
 * Time: 2020/7/12
 * Author: Mightted
 * Description: 方位实例，用于记录物体当前的仰角和旋转角等
 */
class PositionInstance {
    private var xzRadian: Float = 0f
    private var yRadian: Float = 0f

    private var xzAngle: Float = 0f
    private var yAngle: Float = 0f


    /**
     * [x] 在x轴上的偏移量，正表示往正方向移动
     * [y]在y轴上的偏移量，正表示往正方向移动
     */
    fun moveCamera(x: Float, y: Float) {
        // 旧的偏移量加上新的偏移量
        xzAngle = PI.toFloat() * x * 100f / 180 + xzRadian
        yAngle = PI.toFloat() * y * 100f / 180 + yRadian

        // 防止到达顶端或者底端，产生万向节死锁
        yAngle = yAngle.coerceIn(-0.5f * PI.toFloat() + 0.02f, 0.5f * PI.toFloat() - 0.02f)
//        if (yAngle >= 0.5f * PI.toFloat() - 0.02f) {
//            yAngle = 0.5f * PI.toFloat() - 0.02f
//        } else if (yAngle <= -0.5f * PI.toFloat() + 0.02f) {
//            yAngle = -0.5f * PI.toFloat() + 0.02f
//        }

    }


    fun getVectorByRadius(radius: Float, callback: (x: Float, y: Float, z: Float) -> Unit) {
        val xzRadius = cos(yAngle) * radius
        callback(
            sin(-xzAngle) * xzRadius,
            -sin(yAngle) * radius,
            cos(xzAngle) * xzRadius
        )
//        return floatArrayOf(
//            sin(-xzAngle) * xzRadius,
//            -sin(yAngle) * radius,
//            cos(xzAngle) * xzRadius
//        )
    }

    fun stopMove(x: Float, y: Float) {
        xzRadian += PI.toFloat() * x * 100f / 180
        yRadian += PI.toFloat() * y * 100f / 180
    }
}