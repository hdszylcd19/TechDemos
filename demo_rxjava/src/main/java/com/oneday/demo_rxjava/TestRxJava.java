package com.oneday.demo_rxjava;

import android.os.SystemClock;

import java.util.concurrent.Callable;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * Desc:
 *
 * @author JiLin
 * @version 1.0
 * @since 2021/1/20 17:34
 */
public class TestRxJava {
    public static void main(String[] args) {
//        simpleUsage();
        fromCallableUsage();
    }

    private static void fromCallableUsage() {
        Observable
                .fromCallable(new Callable<String>() {
                    @Override
                    public String call() throws Exception {
                        SystemClock.sleep(2000); //模拟耗时操作
                        return "fromCallable";
                    }
                })
                .subscribeOn(Schedulers.io()) //IO线程处理数据
                .observeOn(AndroidSchedulers.mainThread()) //Android主线程回调
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        logThreadName("onSubscribe()");
                    }

                    @Override
                    public void onNext(@NonNull String s) {
                        logThreadName("onNext(" + s + ")");
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        logThreadName("onError()");
                    }

                    @Override
                    public void onComplete() {
                        logThreadName("onComplete()");
                    }
                });

    }

    /*RxJava简单用法，如果只是这样使用，RxJava并不具备线程切换的魔法*/
    private static void simpleUsage() {
        // 第一步：创建被观察者
        Observable<String> observable = Observable
                .create(new ObservableOnSubscribe<String>() {
                    @Override
                    public void subscribe(@NonNull ObservableEmitter<String> emitter) throws Throwable {
                        emitter.onNext("第一次");
                        emitter.onNext("第二次");
                        emitter.onNext("第三次");
                        emitter.onComplete();
                    }
                });

        // 第二步：创建观察者
        Observer<String> observer = new Observer<String>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                System.out.println("订阅成功");
            }

            @Override
            public void onNext(@NonNull String s) {
                // 常规事件，可以传递各种各样的数据
                System.out.println("s = " + s);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                // 异常事件，当被观察者发送异常事件后，那么其他的事件就不会再继续发送了
                System.out.println("e = " + e);
            }

            @Override
            public void onComplete() {
                // 结束事件，当观察者接收到结束事件后，就不会再接收后续被观察者发送来的事件
                System.out.println("onComplete()");
            }
        };

        // 第三步：被观察者订阅观察者
        observable.subscribe(observer);
    }

    private static void logThreadName(String prefix) {
        System.out.println(prefix + " 所在线程：" + Thread.currentThread().getName());
    }
}
