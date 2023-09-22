package com.shivamkumarjha.baserecyclerview

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.shivamkumarjha.baserecyclerview.baserecyclerview.BaseViewType
import com.shivamkumarjha.baserecyclerview.baserecyclerview.ITEM_IMAGE
import com.shivamkumarjha.baserecyclerview.baserecyclerview.databinder.MainImage
import com.shivamkumarjha.baserecyclerview.baserecyclerview.databinder.MainImages
import com.shivamkumarjha.baserecyclerview.baserecyclerview.databinder.TextHeader
import com.shivamkumarjha.baserecyclerview.baserecyclerview.util.dp
import com.shivamkumarjha.baserecyclerview.baserecyclerview.util.toast
import com.shivamkumarjha.baserecyclerview.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val mainAdapter: MainAdapter by lazy {
        MainAdapter(binding.rvDemo.recycledViewPool) { text ->
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
            addItemDecoration(StickyHeaderDecoration(mainAdapter, binding.root))
        }

        val mainImages = MainImages(
            arrayListOf(
                MainImage("https://img.freepik.com/premium-photo/cat-looking-out-desert-landscape_839976-84.jpg"),
                MainImage("https://i.pinimg.com/originals/cd/13/c8/cd13c8ed6e74a90cf9deba9b09d63724.jpg"),
                MainImage("https://e0.pxfuel.com/wallpapers/1009/82/desktop-wallpaper-animals-landscape-cats.jpg"),
                MainImage("https://pbs.twimg.com/media/C2-eHBHWIAAWdq9.jpg"),
                MainImage("https://www.boredpanda.com/blog/wp-content/uploads/2020/01/cat-landscape-16-5e2e60a2dc5c5__605.jpg"),
            )
        )
        val items: ArrayList<BaseViewType> = arrayListOf()
        repeat(5) {
            items.add(TextHeader("Shivam", 16f))
            items.add(TextHeader("Kumar", 14f))
            items.add(TextHeader("Jha", 20f))
            items.add(mainImages)
        }
        mainAdapter.submitList(items)

        //List manipulation demo
        lifecycleScope.launch(Dispatchers.IO) {
            delay(3000)
            Log.d(TAG, "addItem")
            mainAdapter.addItem(TextHeader("addItem", 12f))
            delay(3000)
            Log.d(TAG, "addItem 0")
            mainAdapter.addItem(0, TextHeader("addItem at 0", 12f))
            delay(3000)
            Log.d(TAG, "addItems")
            mainAdapter.addItems(mainImages.images)
            delay(3000)
            Log.d(TAG, "addItems 0")
            mainAdapter.addItems(0, mainImages.images)
            delay(3000)
            Log.d(TAG, "removeIndex 0")
            mainAdapter.removeIndex(0)
            delay(3000)
            Log.d(TAG, "removeLast")
            mainAdapter.removeLast()
            delay(3000)
            Log.d(TAG, "removeView $ITEM_IMAGE")
            mainAdapter.removeView(ITEM_IMAGE)
            delay(3000)
            Log.d(TAG, "updateItem 0")
            mainAdapter.updateItem(0, TextHeader("updateItem at 0", 12f))
            delay(3000)
            Log.d(TAG, "clearItems")
            mainAdapter.clearItems()
        }
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}