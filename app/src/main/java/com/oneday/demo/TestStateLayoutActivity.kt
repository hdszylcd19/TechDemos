package com.oneday.demo


import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.oneday.base.BaseVMActivity
import com.oneday.demo.viewmodel.TestStateLayoutViewModel
import com.oneday.utils.DensityUtil
import kotlinx.android.synthetic.main.activity_test_state_layout.*

/**
 * 测试状态根布局StateLayout
 */
class TestStateLayoutActivity : BaseVMActivity<TestStateLayoutViewModel>() {

    override fun getLayoutResId(): Int = R.layout.activity_test_state_layout

    override fun init(savedInstanceState: Bundle?) {
        stateLayout?.isUseAnimation = true
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
                },
                onLoading = {

                }
            )
        }
    }

    fun requestSuccess(view: View) {
        mViewModel.requestSuccess()
    }

    fun requestSuccessEmpty(view: View) {
        mViewModel.requestSuccessEmpty()
    }

    fun requestNoNetwork(view: View) {
        mViewModel.requestNoNetwork()
    }

    fun requestError(view: View) {
        mViewModel.requestError()
    }

    class TestStateAdapter<String>(list: List<String>) : RecyclerView.Adapter<TestViewHolder>() {
        private val mList: List<String> = list

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestViewHolder {
            val textView = TextView(parent.context)
            textView.gravity = Gravity.CENTER
            textView.layoutParams = RecyclerView.LayoutParams(MATCH_PARENT, DensityUtil.dp2px(36f))
            textView.setTextColor(Color.BLACK)
            textView.textSize = 24f
            return TestViewHolder(textView)
        }

        override fun getItemCount(): Int {
            return mList.size
        }

        override fun onBindViewHolder(holder: TestViewHolder, position: Int) {
            val item = mList[position]
            holder.tv.text = item.toString()
        }
    }

    class TestViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tv: TextView = itemView as TextView
    }
}