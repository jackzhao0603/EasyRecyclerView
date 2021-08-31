package com.jackzhao.easyrecyclerviewapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.jackzhao.easyrecyclerview.EasyRecyclerView
import com.jackzhao.easyrecyclerview.data.SimpleTextData

class MainActivity : AppCompatActivity() {
    private lateinit var dataList: List<*>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val recyclerView: EasyRecyclerView = findViewById(R.id.rv_main)
        val swipeRefresh: SwipeRefreshLayout = findViewById(R.id.swipe_refresh_layout)



        dataList = ArrayList<Any>()
        for (i in 1..10) {
            (dataList as ArrayList<Any>).add(SimpleTextData("title$i", "context$i"))
            (dataList as ArrayList<Any>).add(SelfTextData("title$i"))
        }


        // Simple Adapter
//        val recyclerView: EasyRecyclerView = findViewById(R.id.rv_main)
//        val adapter = SimpleTextAdapter()
//        adapter.dataList = dataList
//        recyclerView.adapter = adapter
//        recyclerView.addItemDecoration(this);

        // Self Adapter
        recyclerView.addItemDecoration(this);
        val adapter = SelfTextAdapter()
        dataList = adapter.bindData(applicationContext, dataList)
        recyclerView.adapter = adapter


        // Enable Drag(options)
//        recyclerView.enableDrag(adapter, object : EasyRecyclerView.Draglister {
//            override fun onItemMoved(fromPosition: Int, toPosition: Int) {
//                Toast.makeText(
//                    applicationContext,
//                    "$fromPosition  --> $toPosition", Toast.LENGTH_LONG
//                ).show();
//            }
//
//            override fun onItemSwiped(pos: Int, data: Any?) {
//                Toast.makeText(
//                    applicationContext,
//                    "$pos  --> $data", Toast.LENGTH_LONG
//                ).show();
//            }
//        })

        swipeRefresh.setOnRefreshListener {
            Thread {
                Thread.sleep(1000)
                (dataList as MutableList<Any?>).reverse()
                runOnUiThread {
                    swipeRefresh.isRefreshing = false
                }
            }.start()
        }


    }


}