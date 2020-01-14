package com.hsw.moonlight;

import android.util.Log;

public class TestJava {
    public static void main(String[] args) {
        new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Log.i("测试日志","开始");
    }
}
//object TestJava {
//    @JvmStatic
//    fun main(args: Array<String>) {
//        Thread(Runnable {
//            try {
//                Thread.sleep(1000)
//            } catch (e: InterruptedException) {
//                e.printStackTrace()
//            }
//        })
//    }
//}

//object TestJava {
//    @JvmStatic
//    fun main(args: Array<String>) {
//        Thread { try {
//                Thread.sleep(1000)
//            } catch (e: InterruptedException) {
//                e.printStackTrace()
//            }
//        }
//    }
//}