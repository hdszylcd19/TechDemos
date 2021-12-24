package com.oneday.demo

import android.graphics.Color
import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_recycler_view.*

class RecyclerViewActivity : AppCompatActivity() {
    val data = listOf(
        "单选/多选",
        "判断题",
        "填空(主)",
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
    lateinit var mTestAdapter: TestAdapter<String>

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.recycler_view, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_notify_data_set_changed -> {
                mTestAdapter.notifyDataSetChanged()
            }
            R.id.action_notify_item_changed -> {
                mTestAdapter.notifyItemChanged(0)
            }
            R.id.action_notify_item_moved -> {
                mTestAdapter.notifyItemMoved(0, 1)
            }
            R.id.action_notify_item_range_changed -> {
                mTestAdapter.notifyItemRangeChanged(0, 5)
            }
            R.id.action_notify_item_range_inserted -> {
                mTestAdapter.notifyItemRangeInserted(0, 5)
            }
            R.id.action_notify_item_range_removed -> {
                mTestAdapter.notifyItemRangeRemoved(0, 5)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view)

        val arrayList = ArrayList<String>()
        for (i in 0 until 100) {
            arrayList.addAll(data)
        }

        val linearLayoutManager = LinearLayoutManager(this)
        rv.layoutManager = linearLayoutManager
        mTestAdapter = TestAdapter(arrayList)
        rv.adapter = mTestAdapter
    }

    class TestAdapter<T> : RecyclerView.Adapter<TestViewHolder> {
        private val mList: List<T>

        constructor(list: List<T>) : super() {
            mList = list
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestViewHolder {
            val textView = TextView(parent.context)
            textView.gravity = Gravity.CENTER
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