package com.hsw.moonlight

enum class Color {
    RED, BLACK, BLUE, GREEN, WHITE
}

fun main(args: Array<String>) {
    var color: Color = Color.BLUE
    println(Color.values().joinToString { it.name })//   以数组的形式，返回枚举值
    println(Color.valueOf("RED"))// 转换指定 name 为枚举值，若未匹配成功，会抛出IllegalArgumentException
    println(color.name)//获取枚举名称
    println(color.ordinal) //获取枚举值在所有枚举数组中定义的顺序
}