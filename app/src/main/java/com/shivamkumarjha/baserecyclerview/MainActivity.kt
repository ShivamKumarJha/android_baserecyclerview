package com.shivamkumarjha.baserecyclerview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.shivamkumarjha.baserecyclerview.baserecyclerview.BaseViewType
import com.shivamkumarjha.baserecyclerview.baserecyclerview.databinder.TextHeader
import com.shivamkumarjha.baserecyclerview.baserecyclerview.util.dp
import com.shivamkumarjha.baserecyclerview.baserecyclerview.util.toast
import com.shivamkumarjha.baserecyclerview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val mainAdapter: MainAdapter by lazy {
        MainAdapter { text ->
            toast(text)
        }
    }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        initMainRecyclerView()
    }

    private fun initMainRecyclerView() {
        binding.rvDemo.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            setHasFixedSize(true)
            adapter = mainAdapter
            addItemDecoration(MainItemDecorator { position, rect ->
                rect.top = when (position) {
                    0 -> 24.dp
                    else -> 8.dp
                }
                rect.bottom = when (position) {
                    (mainAdapter.itemCount - 1) -> 48.dp
                    else -> 0.dp
                }
                rect.apply {
                    left = 16.dp
                    right = 16.dp
                }
            })
        }

        val items: ArrayList<BaseViewType> = arrayListOf()
        repeat(50) {
            items.add(TextHeader("Shivam", 16f))
            items.add(TextHeader("Kumar", 14f))
            items.add(TextHeader("Jha", 20f))
        }
        mainAdapter.submitList(items)
    }
}