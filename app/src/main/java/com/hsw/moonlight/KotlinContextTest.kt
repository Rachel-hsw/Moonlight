package com.hsw.moonlight

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import kotlin.coroutines.Continuation
import kotlin.coroutines.ContinuationInterceptor

class KotlinContextTest {
    val dateFormat = SimpleDateFormat("HH:mm:ss:SSS")

    val now = {
        dateFormat.format(Date(System.currentTimeMillis()))
    }

    //22:00:31:782 [main @coroutine#1] 2
    fun log(msg: Any?) = println("${now()} [${Thread.currentThread().name}] $msg")

    class MyContinuationInterceptor : ContinuationInterceptor {
        override val key = ContinuationInterceptor
        override fun <T> interceptContinuation(continuation: Continuation<T>) = MyContinuation(continuation)
    }

    class MyContinuation<T>(val continuation: Continuation<T>) : Continuation<T> {
        override val context = continuation.context
        override fun resumeWith(result: Result<T>) {
            log("<MyContinuation> $result")
            continuation.resumeWith(result)
        }

        val dateFormat = SimpleDateFormat("HH:mm:ss:SSS")

        val now = {
            dateFormat.format(Date(System.currentTimeMillis()))
        }

        //22:00:31:782 [main @coroutine#1] 2
        fun log(msg: Any?) = println("${now()} [${Thread.currentThread().name}] $msg")
    }

    /*这可能是迄今而止我们给出的最复杂的例子了，不过请大家不要被它吓到，它依然很简单。我们通过 launch 启动了一个协程，为它指定了我们自己的拦截器作为上下文，
    紧接着在其中用 async 启动了一个协程，async 与 launch 从功能上是同等类型的函数，它们都被称作协程的 Builder 函数，不同之处在于 async 启动的 Job 也就是实
    际上的 Deferred 可以有返回结果，可以通过 await 方法获取。*/
    suspend fun main() {
        GlobalScope.launch(MyContinuationInterceptor()) {
            log(1)
            val job = async {
                log(2)
                delay(1000)
                log(3)
                "Hello"
            }
            log(4)
            val result = job.await()
            log("5. $result")
        }.join()
        log(6)
    }
}