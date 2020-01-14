package com.hsw.moonlight

import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import kotlin.concurrent.thread

class KotlinThreadTest {
    val dateFormat = SimpleDateFormat("HH:mm:ss:SSS")

    val now = {
        dateFormat.format(Date(System.currentTimeMillis()))
    }

    //22:00:31:782 [main @coroutine#1] 2
    fun log(msg: Any?) = println("${now()} [${Thread.currentThread().name}] $msg")

    //线程的启动:直接启动
    var thread=thread {

    }
    val myThread = thread(start = false) {
        //do what you want
    }
    //DEFAULT	立即执行协程体
    //ATOMIC	立即执行协程体，但在开始运行之前无法取消
    //UNDISPATCHED	立即在当前线程执行协程体，直到第一个 suspend 调用
    //LAZY	只有在需要的情况下运行

    // main 函数 支持 suspend 是从 Kotlin 1.3 开始的。另外，main 函数省略参数也是 Kotlin 1.3 的特性。后面的示例没有特别说明都是直接运行在 suspend main 函数当中。
    suspend fun main(args: Array<String>/*可以省略*/) {
        //线程的启动：需要时启动
        myThread.start()
        //协程的启动：上下文、启动模式、协程体
        GlobalScope.launch {
            //do what you want
        }
        /*default启动模式 DEFAULT 是饿汉式启动，launch 调用后，会立即进入待调度状态，一旦调度器 OK 就可以开始执行。*/
        log(1)
        val job = GlobalScope.launch {
            log(2)
        }
        log(3)
        //Suspend function 'join' should be called only from a coroutine or another suspend function
        //只能从协程或其他挂起函数调用挂起函数“join”
        job.join()
        log(4)
        //当然，我们说 Kotlin 是一门跨平台的语言，因此上述代码还可以运行在 JavaScript 环境中，例如 Nodejs。
        // 在 Nodejs 中，Kotlin 协程的默认调度器则并没有实现线程的切换，输出结果也会略有不同，这样似乎更符合 JavaScript 的执行逻辑。

        /*LAZY 是懒汉式启动，launch 后并不会有任何调度行为，协程体也自然不会进入执行状态，直到我们需要它执行的时候。
        这其实就有点儿费解了，什么叫我们需要它执行的时候呢？就是需要它的运行结果的时候， launch 调用后会返回一个 Job 实例，对于这种情况，我们可以：
        调用 Job.start，主动触发协程的调度执行
        调用 Job.join，隐式的触发协程的调度执行*/
        log(1)
        val job1 = GlobalScope.launch(start = CoroutineStart.LAZY) {
            log(2)
        }
        log(3)
        job1.start()
        log(4)

        /*ATOMIC 只有涉及 cancel 的时候才有意义，cancel 本身也是一个值得详细讨论的话题，在这里我们就简单认为 cancel 后协程会被取消掉，也就是不再执行了。
        那么调用 cancel 的时机不同，结果也是有差异的，例如协程调度之前、开始调度但尚未执行、已经开始执行、执行完毕等等。(和DEFAULT模式不一样 )*/
        log(1)
        val job2 = GlobalScope.launch(start = CoroutineStart.ATOMIC) {
            log(2)
        }
        job2.cancel()
        log(3)

        /*有了前面的基础，UNDISPATCHED 就很容易理解了。协程在这种模式下会直接开始在当前线程下执行，直到第一个挂起点，这听起来有点儿像前面的 ATOMIC，
        不同之处在于 UNDISPATCHED 不经过任何调度器即开始执行协程体。当然遇到挂起点之后的执行就取决于挂起点本身的逻辑以及上下文当中的调度器了。
        我们还是以这样一个例子来认识下 UNDISPATCHED 模式，按照我们前面的讨论，协程启动后会立即在当前线程执行，因此 1、2 会连续在同一线程中执行，delay 是挂起点，
        因此 3 会等 100ms 后再次调度，这时候 4 执行，join 要求等待协程执行完，因此等 3 输出后再执行 5。以下是运行结果：*/
        log(1)
        val job3 = GlobalScope.launch(start = CoroutineStart.UNDISPATCHED) {
            log(2)
            delay(100)
            log(3)
        }
        log(4)
        job3.join()
        log(5)

    }


}