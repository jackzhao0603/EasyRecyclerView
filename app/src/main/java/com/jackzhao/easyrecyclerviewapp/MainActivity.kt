package com.jackzhao.easyrecyclerviewapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.jackzhao.easyrecyclerview.EasyRecyclerView
import com.jackzhao.easyrecyclerview.adapter.BaseAdapter
import com.jackzhao.easyrecyclerview.adapter.DragAdapter
import com.jackzhao.easyrecyclerview.adapter.SimpleTextAdapter
import com.jackzhao.easyrecyclerview.data.SimpleTextData

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val dataList = ArrayList<Any>()
        for (i in 1..10) {
            dataList.add(SimpleTextData("title$i", "context$i"))
            dataList.add(SelfTextData("title$i"))
        }


        // Simple Adapter
//        val recyclerView: EasyRecyclerView = findViewById(R.id.rv_main)
//        val adapter = SimpleTextAdapter()
//        adapter.dataList = dataList
//        recyclerView.adapter = adapter
//        recyclerView.addItemDecoration(this);

        // Self Adapter
        val recyclerView: EasyRecyclerView = findViewById(R.id.rv_main)
        val adapter = SelfTextAdapter()
        adapter.dataList = dataList
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(this);


        // Drag Adapter
        val dragAdapter = DragAdapter()
        dragAdapter.setOriginalAdpater(adapter)
        dragAdapter.addTouchHelperRecyclerView(
            recyclerView,
            object : BaseAdapter.ItemChangedListener<Any> {
                override fun itemMoved(fromPosition: Int, toPosition: Int) {
                    Toast.makeText(
                        applicationContext,
                        "$fromPosition  --> $toPosition", Toast.LENGTH_LONG
                    ).show();
                }

                override fun itemSwiped(pos: Int, data: Any?) {
                    Toast.makeText(
                        applicationContext,
                        "$pos  --> $data", Toast.LENGTH_LONG
                    ).show();
                }
            })
        recyclerView.adapter = dragAdapter
        recyclerView.addItemDecoration(this);
    }


}