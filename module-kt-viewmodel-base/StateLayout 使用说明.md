# StateLayout 使用说明

在我们日常的 APP 开发中，每个页面加载数据时，都有可能存在好多种异常状态。

比如：没有网络、请求超时、请求错误、 请求成功但是数据为空等等...

每一种异常状态都对应着不同的状态显示页面。当点击异常状态的图片按钮时，又会去重新请求数据刷新页面状态。如果每个页面都单独去处理的话，将会非常繁琐，也非常容易漏掉某一个界面。

StateLayout 就是为了解决这种情况而出现的。StateLayout 是一个状态布局自定义控件，在布局文件中，我们只需要把页面的根布局放在 StateLayout 中即可。

```xml
<com.oneday.widget.StateLayout
    android:id="@+id/stateLayout"
    android:layout_width="match_parent"
    android:layout_height="0dp"
    app:layout_constraintBottom_toBottomOf="parent"
    app:layout_constraintEnd_toEndOf="parent"
    app:layout_constraintStart_toStartOf="parent"
    app:layout_constraintTop_toBottomOf="@id/title">

	<!-- 让页面根布局作为 StateLayout 的唯一一个子 View 即可 -->
    <androidx.recyclerview.widget.RecyclerView
        android:id="@+id/rv"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />

</com.oneday.widget.StateLayout>
```

**注意：StateLayout 有且只能有一个子 View！**

StateLayout 默认支持了 5 种状态类型。

```java
public static final int CONTENT_STATE = 0; /*正常内容状态类型*/
public static final int ERROR_STATE = 1; /*错误状态类型*/
public static final int EMPTY_STATE = 2; /*数据为空状态类型*/
public static final int TIMEOUT_STATE = 3; /*超时状态类型*/
public static final int NO_NETWORK_STATE = 4; /*没有网络状态类型*/
```

这 5 种类型分别对应了 5 种显示方法。

```java
/**
 * 展示内容的界面
 */
public void showContentView();

/**
 * 展示错误的界面
 */
public void showErrorView();

/**
 * 展示空数据的界面
 */
public void showEmptyView();

/**
 * 展示超时的界面
 */
public void showTimeoutView();

/**
 * 展示没有网络的界面
 */
public void showNoNetworkView();
```

当需要显示对应的状态布局时，调用对应的方法即可。就像下面这样：

```kotlin
mViewModel.dataListState.observe(this) {
    parseState(it,
        onSuccess = { list ->
            val linearLayoutManager = LinearLayoutManager(this)
            rv.layoutManager = linearLayoutManager
            rv.adapter = TestStateAdapter(list)
            stateLayout?.showContentView()
        },
        onSuccessEmpty = {
            stateLayout?.showEmptyView()
        },
        onNoNetwork = {
            stateLayout?.showNoNetworkView()
        },
        onError = {
            stateLayout?.showErrorView()
        })
}
```

其中，4 种异常状态都有各自的 4 种异常状态默认布局（上方一个异常状态图片，下方一段描述信息）。

当需要修改默认的异常状态图片或者文字提示时，可以使用各显示方法对应的重载方法即可。

如果默认异常状态布局不满足需求时，可以使用自定义属性指定自己的异常状态布局。

```xml
<declare-styleable name="StateLayout">
    <!--错误状态布局-->
    <attr name="err_layout" format="reference" />
    <!-- 错误提示图标 -->
    <attr name="err_img" format="reference" />
    <!-- 错误提示文字 -->
    <attr name="err_tip" format="string" />
    <!--空数据状态布局-->
    <attr name="empty_layout" format="reference" />
    <!-- 空数据提示图标 -->
    <attr name="empty_img" format="reference" />
    <!-- 空数据提示文字 -->
    <attr name="empty_tip" format="string" />
    <!--没有网络状态布局-->
    <attr name="no_network_layout" format="reference" />
    <!-- 没有网络提示图标 -->
    <attr name="no_network_img" format="reference" />
    <!-- 没有网络提示文字 -->
    <attr name="no_network_tip" format="string" />
    <!--加载超时状态布局-->
    <attr name="timeout_layout" format="reference" />
    <!-- 加载超时提示图标 -->
    <attr name="timeout_img" format="reference" />
    <!-- 加载超时提示文字 -->
    <attr name="timeout_tip" format="string" />
</declare-styleable>
```