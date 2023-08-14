package com.shivamkumarjha.baserecyclerview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.shivamkumarjha.baserecyclerview.baserecyclerview.BaseViewType
import com.shivamkumarjha.baserecyclerview.baserecyclerview.databinder.TextHeader
import com.shivamkumarjha.baserecyclerview.baserecyclerview.decorator.MarginItemDecorator
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
            addItemDecoration(MarginItemDecorator(16.dp, 24.dp, 16.dp, 48.dp))
        }

        val items: ArrayList<BaseViewType> = arrayListOf()
        items.add(TextHeader("Shivam", 16f))
        items.add(TextHeader("Kumar", 14f))
        items.add(TextHeader("Jha", 20f))
        mainAdapter.submitList(items)
    }
}