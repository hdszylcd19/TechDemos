# OkHttp请求调用链

> OkHttp 版本：4.9.3

一个简单的 OkHttp 请求示例如下：

```kotlin
private val okHttpClient = OkHttpClient()
fun request(url: String): String? {
    val request = Request.Builder().url(url).build()
    return try {
        val response = okHttpClient.newCall(request).execute()
        response.body?.string()
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}
```

根据示例代码，跟踪 OkHttp 调用链。我们首先来看一下 execute() 方法。省略掉一些无关的逻辑，我们只关心最核心的实现。

```kotlin
// okhttp3.internal.connection.RealCall.kt

  override fun execute(): Response {
    // ... 省略无关代码
    try {
      client.dispatcher.executed(this)
      return getResponseWithInterceptorChain()
    } finally {
      client.dispatcher.finished(this)
    }
  }
```



```kotlin
  @Throws(IOException::class)
  internal fun getResponseWithInterceptorChain(): Response {
    // Build a full stack of interceptors.
    // 构建OkHttp请求责任链
    val interceptors = mutableListOf<Interceptor>()
    interceptors += client.interceptors
    interceptors += RetryAndFollowUpInterceptor(client)
    interceptors += BridgeInterceptor(client.cookieJar)
    interceptors += CacheInterceptor(client.cache)
    interceptors += ConnectInterceptor
    if (!forWebSocket) {
      interceptors += client.networkInterceptors
    }
    interceptors += CallServerInterceptor(forWebSocket)

    val chain = RealInterceptorChain(
        call = this,
        interceptors = interceptors,
        index = 0,
        exchange = null,
        request = originalRequest,
        connectTimeoutMillis = client.connectTimeoutMillis,
        readTimeoutMillis = client.readTimeoutMillis,
        writeTimeoutMillis = client.writeTimeoutMillis
    )

    var calledNoMoreExchanges = false
    try {
      val response = chain.proceed(originalRequest)
      if (isCanceled()) {
        response.closeQuietly()
        throw IOException("Canceled")
      }
      return response
    } catch (e: IOException) {
      calledNoMoreExchanges = true
      throw noMoreExchanges(e) as Throwable
    } finally {
      if (!calledNoMoreExchanges) {
        noMoreExchanges(null)
      }
    }
  }
```