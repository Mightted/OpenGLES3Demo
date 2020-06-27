package com.hxs.opengles3demo

/**
 * Time: 2020/6/25
 * Author: Mightted
 * Description:
 */
object Geometry {

}

// 点类
data class Point(val x:Float, val y:Float, val z:Float)

// 向量类
data class Vector(val x:Float, val y:Float, val z: Float)

// 射线类 由原点和方向向量确定
data class Ray(val point:Point, val vector: Vector)

// 平面类，由一个点和一条法线向量决定
data class Plane(val point: Point, val normal:Vector)




