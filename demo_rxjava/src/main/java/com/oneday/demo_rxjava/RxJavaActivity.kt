package com.oneday.demo_rxjava

import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.*
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.functions.Consumer
import io.reactivex.rxjava3.functions.Function3
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_rx_java.*
import java.util.concurrent.Callable
import java.util.concurrent.TimeUnit
import kotlin.random.Random


class RxJavaActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "RxJava"
    }

    private val mQuestionNames = listOf(
        "单选题",
        "多选题",
        "判断题",
        "填空(主)-手写",
        "填空(主)-键盘",
        "填空(客)",
        "填空(客)-多答案",
        "填空(客)-多键盘类型",
        "简答题",
        "解答题",
        "阅读题",
        "听力题",
        "断句题",
        "字词拼写题",
        "句子拼写题",
        "字词听写题"
    )
    private var mQuestionCodes: List<Int>

    init {
        mQuestionCodes = ArrayList(mQuestionNames.size)
        for (i in mQuestionNames.indices) {
            (mQuestionCodes as ArrayList<Int>).add(i)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rx_java)


//        testCreate()
//        testMap()
//        testFlatMap()
        testFlatMapAndConcatMap()
//        testMerge()
//        testZip()
//        testFromCallable()
//        testMultiSubscribeOn()
//        decorator()
    }

    private fun decorator() {
        // 创建一个手机
        val phone = Phone()

        // 给手机套上手机壳
        val phoneShell = PhoneShell(phone)

        // 给手机贴上钢化膜
        val phoneCover = PhoneCover(phoneShell)

        // 最终的手机结构
        phoneCover.structure()
    }

    /**
     * 当多次调用subscribeOn()时，只有第一次的subscribeOn()生效。
     *
     * 如果你理解了订阅的过程，其实该问题很好理解，subscribeOn是指定上游的observable在哪个线程中执行，
     * 如果我们执行多次的subscribeOn的话，从下游的observer到上游的observable的订阅过程，
     * 最开始调用的subscribeOn返回的observable会把后面执行的subscribeOn返回的observable给覆盖了，
     * 因此我们感官的是只有第一次的subscribeOn能生效。

     * 那如何才能知道它实际在里面生效了呢，我们可以通过doOnSubscribe来监听切实发生线程切换了。
     *
     *
     *
     * 当多次调用observeOn()时，只有最后一次的observeOn()生效。
     *
     * observeOn是指定下游的observer在哪个线程中执行，所以这个更好理解，看observeOn下一个observer是哪一个，
     * 所以多次调用observeOn肯定是最后一个observeOn控制有效。
     */
    private fun testMultiSubscribeOn() {
        Observable
            .fromCallable(object : Callable<String> {
                override fun call(): String {
                    logThreadName("call()")
                    SystemClock.sleep(2000)
                    return "fromCallable"
                }
            })
            .doOnSubscribe(object : Consumer<Disposable> {
                override fun accept(t: Disposable?) {
                    logThreadName("doOnSubscribe()")
                }
            })
            .subscribeOn(Schedulers.newThread())
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .observeOn(Schedulers.newThread())
            .observeOn(Schedulers.single())
            .subscribe(object : Observer<String> {
                override fun onSubscribe(d: Disposable?) {
                    logThreadName("onSubscribe()订阅成功！")
                }

                override fun onNext(t: String?) {
                    logThreadName("onNext()")
                }

                override fun onComplete() {
                    logThreadName("onComplete()")
                }

                override fun onError(e: Throwable?) {
                    logThreadName("onError()")
                }
            })
    }

    private fun testFromCallable() {
        Observable
            .fromCallable(object : Callable<String> {
                override fun call(): String {
                    SystemClock.sleep(2000)
                    return "fromCallable"
                }
            })
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<String> {
                override fun onSubscribe(d: Disposable?) {

                }

                override fun onNext(t: String?) {

                }

                override fun onComplete() {

                }

                override fun onError(e: Throwable?) {

                }
            })
    }

    private fun testZip() {
        /*获取名字Observable*/
        val nameObservable = Observable
            .fromCallable(object : Callable<String> {
                override fun call(): String {
                    log("开始准备获取名字数据源")
                    SystemClock.sleep(3000)
                    val name = "路飞"
                    logThreadName("名字数据源：$name")
                    return name
                }
            })
            .subscribeOn(Schedulers.io())

        /*获取年龄Observable*/
        val ageObservable = Observable
            .fromCallable(object : Callable<Int> {
                override fun call(): Int {
                    log("开始准备获取年龄数据源")
                    SystemClock.sleep(3000)
                    val age = 17
                    logThreadName("年龄数据源：$age")
                    return age
                }
            })
            .subscribeOn(Schedulers.io())

        /*获取饥饿状态Observable*/
        val hungryObservable = Observable
            .fromCallable(object : Callable<Boolean> {
                override fun call(): Boolean {
                    log("开始准备获取饥饿数据源")
                    SystemClock.sleep(3000)
                    val hungry = Random.nextBoolean()
                    logThreadName("饥饿数据源：$hungry")
                    return hungry
                }
            })
            .subscribeOn(Schedulers.io())

        Observable.zip(
            nameObservable,
            ageObservable,
            hungryObservable,
            object : Function3<String, Int, Boolean, Person> {
                override fun apply(t1: String, t2: Int, t3: Boolean): Person {
                    val person = Person(t1, t2, t3)
                    logThreadName("数据合并完成：$person")
                    return person
                }
            })
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<Person> {
                override fun onComplete() {
                    logThreadName("onComplete")
                }

                override fun onSubscribe(d: Disposable?) {
                    logThreadName("onSubscribe")
                }

                override fun onNext(t: Person?) {
                    logThreadName("onNext：$t")
                }

                override fun onError(e: Throwable?) {
                    logThreadName("onError")
                }
            })
    }

    private fun testMerge() {
        /*获取网络数据Observable*/
        val netObservable = Observable
            .fromCallable(object : Callable<String> {
                override fun call(): String {
                    logThreadName("netObservable")
                    SystemClock.sleep(3000) //模拟获取网络数据耗时
                    return "网络数据"
                }
            })
            .subscribeOn(Schedulers.io()) //指定在IO线程执行

        /*获取本地数据Observable*/
        val localObservable = Observable
            .fromCallable(object : Callable<String> {
                override fun call(): String {
                    logThreadName("localObservable")
                    SystemClock.sleep(3000) //模拟获取本地数据耗时
                    return "本地数据"
                }
            })
            .subscribeOn(Schedulers.io()) //指定在IO线程执行

        Observable
            .merge(netObservable, localObservable)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<String> {
                val builder: StringBuilder = StringBuilder() //用以记录网络和本地缓存数据

                override fun onComplete() {
                    logThreadName("onComplete()")
                    log("获取数据完成：$builder") //执行到这里，说明netObservable和localObservable都已回调完毕
                }

                override fun onSubscribe(d: Disposable) {
                    logThreadName("onSubscribe()")
                }

                override fun onNext(t: String) {
                    logThreadName("onNext()")
                    builder.append(t).append(" | ") //netObservable和localObservable都有可能先回调，具体要看线程调度
                    log("数据源：$t")
                }

                override fun onError(e: Throwable) {
                    Log.e(TAG, e.toString())
                }
            })
    }

    /**
     * concatMap和flatMap的功能是一样的， 将一个发射数据的Observable变换为多个Observables，
     * 然后将它们发射的数据放进一个单独的Observable。只不过最后合并ObservablesflatMap采用的merge，
     * 而concatMap采用的是连接(concat)。

     * 总之一句一话,他们的区别在于：concatMap是有序的，flatMap是无序的，concatMap最终输出的顺序与原序列保持一致，
     * 而flatMap则不一定，有可能出现交错。
     */
    private fun testFlatMapAndConcatMap() {
        val createObservable = Observable.just(1, 2, 3, 4, 5, 6, 7, 8, 9)
        val flatMapObservable: Observable<String> = createObservable.flatMap(object :
            io.reactivex.rxjava3.functions.Function<Int, ObservableSource<String>> {
            @Throws(Throwable::class)
            override fun apply(s: Int): ObservableSource<String> {
                return if (s == 2) {
                    // 延时发送数据
                    Observable.fromCallable {
                        s.toString() + "_delay"
                    }.delay(500, TimeUnit.MILLISECONDS)
                } else {
                    Observable.fromCallable {
                        s.toString()
                    }
                }
            }
        })

        flatMapObservable
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<String> {
                override fun onSubscribe(d: Disposable) {
                    log("onSubscribe:" + d.javaClass.name)
                }

                override fun onNext(string: String) {
                    log("onNext: $string")
                }

                override fun onError(e: Throwable) {
                    log("onError: " + e.message)
                }

                override fun onComplete() {
                    log("onComplete()")
                }
            })
    }

    private fun testFlatMap() {
        /*将传入的Iterable拆分成具体对象后，依次发送出来*/
        Observable
            .fromIterable(mQuestionCodes)
            .flatMap {
                logThreadName("flatMap()")
                SystemClock.sleep(2000)
                return@flatMap Observable.just(mQuestionNames[it])
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : BaseObserver<String>() {
                override fun onNext(t: String) {
                    log(t)
                    tv?.text = t
                }
            })
    }

    /**
     * map操作符是通过原始数据类型返回另外一种数据类型
     * 该示例中，是把原始数据类型Int转换为String
     */
    private fun testMap() {
        /*just(T...): 将传入的参数依次发送出来。*/
        Observable
            .just(0, 1, 2, 3, 4, 5, 6, 7)
            .map {
                logThreadName("map()")
                SystemClock.sleep(3000)
                return@map mQuestionNames[it]
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : BaseObserver<String>() {
                override fun onNext(t: String) {
                    log(t)
                    tv?.text = t
                }
            })
    }

    /*RxJava简单用法*/
    private fun testCreate() {
        Observable
            .create(object : ObservableOnSubscribe<String> {
                override fun subscribe(emitter: ObservableEmitter<String>) {
                    for (i in 0..Int.MAX_VALUE) {
                        logThreadName("subscribe()")
                        SystemClock.sleep(2000)
                        emitter.onNext(i.toString())
                    }

                    emitter.onComplete()
                }
            })
            .subscribeOn(Schedulers.io()) //IO线程处理数据
            .observeOn(AndroidSchedulers.mainThread()) //主线程回调
            .subscribe(object : BaseObserver<String>() {
                override fun onNext(t: String) {
                    logThreadName("onNext()")
                    tv?.text = t
                }
            })
    }

    private fun logThreadName(prefix: String) {
        Log.i(TAG, "${prefix}所在线程：" + Thread.currentThread().name)
    }

    private fun log(msg: String) {
        Log.i(TAG, msg)
    }

    abstract inner class BaseObserver<T> : Observer<T> {
        override fun onComplete() {
            logThreadName("onComplete()")
        }

        override fun onSubscribe(d: Disposable) {
            logThreadName("onSubscribe()")
        }

        abstract override fun onNext(t: T)

        override fun onError(e: Throwable) {
            logThreadName("onSubscribe()")
            log(e.toString())
        }
    }

    inner class Person(
        private val name: String = "",
        private val age: Int = 0,
        private val hungry: Boolean = false
    ) {
        override fun toString(): String {
            return "Person = [name:$name age:$age hungry:$hungry]"
        }
    }
}
