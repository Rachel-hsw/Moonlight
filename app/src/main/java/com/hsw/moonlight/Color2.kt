package com.hsw.moonlight
//https://www.cnblogs.com/Jetictors/p/7751662.html
//https://blog.csdn.net/IO_Field/article/details/53320186
enum class Color2(var argb : Int){
    RED(0xFF0000),
    WHITE(0xFFFFFF),
    BLACK(0x000000),
    GREEN(0x00FF00);
}
fun main(args: Array<String>) {
    var color: Color2 = Color2.RED
    println(Color2.values().joinToString { it.name })//   以数组的形式，返回枚举值
    println(Color2.valueOf("WHITE"))//由枚举值的名称获取枚举实例，若未匹配成功，会抛出IllegalArgumentException
    println(Integer.toHexString(Color2.valueOf("WHITE").argb))//由枚举值的名称获取枚举实例，若未匹配成功，会抛出IllegalArgumentException
    println(color.name)//获取枚举名称
    println( Integer.toHexString(Color2.RED.argb))//获取枚举名称
    println(color.ordinal) //获取枚举值在所有枚举数组中定义的顺序
}