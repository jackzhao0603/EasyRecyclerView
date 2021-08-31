# EasyRecyclerView
[![](https://jitpack.io/v/jackzhao0603/EasyRecyclerView.svg)](https://jitpack.io/#jackzhao0603/EasyRecyclerView)
[![Apache License 2.0][1]][2]
[![API][3]][4]

# How to use
## Quick start
1. Add it in your root build.gradle at the end of repositories:

    ```gradle
    allprojects {
        repositories {
            ...
            maven { url "https://jitpack.io" }
        }
    }
    ```
2. Add the dependency
    ```gradle
     dependencies {
	        implementation 'com.github.jackzhao0603:EasyRecyclerView:Tag'
	 }

    ```
3.  Create Data class
    ```java
    public class SelfTextData {
    String title;

    public SelfTextData(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    ```
4. Create ViewHolder
    ```java
    public class SelfTextViewHolder extends BaseViewHolder<SelfTextData> {
        TextView tvTitle;
        TextView tvContent;

        public SelfTextViewHolder(ViewGroup viewGroup) {
            super(viewGroup);
        }


        @Override
        public ViewGroup getViewGroup(ViewGroup viewGroup) {
            return getViewGroupByResourceId(viewGroup, R.layout.item_self_text);
        }


        @Override
        public void bindData(SelfTextData data, ViewGroup viewGroup) {
            tvTitle = viewGroup.findViewById(R.id.tv_title);
            tvContent = viewGroup.findViewById(R.id.tv_content);
            tvTitle.setText(data.getTitle());
            viewGroup.setOnClickListener(view -> {
                Toast.makeText(viewGroup.getContext(),
                        data.getTitle() + " clicked.",
                        Toast.LENGTH_LONG).show();
            });
        }
    }
    ```

5. Create Adapter
    ```java
    public class SelfTextAdapter extends BaseAdapter {
        @Override
        public void registerType() {
            registerViewHolderType(SimpleTextData.class, SimpleTextViewHolder.class);
            registerViewHolderType(SelfTextData.class, SelfTextViewHolder.class);
        }
    }
    ```

6. Add RecyclerView
    ```kotlin
     val recyclerView: EasyRecyclerView = findViewById(R.id.rv_main)

      dataList = ArrayList()
        for (i in 1..10) {
            (dataList as ArrayList<Any>).add(SimpleTextData("title$i", "context$i"))
            (dataList as ArrayList<Any>).add(SelfTextData("title$i"))
        }

        recyclerView.addItemDecoration(this);
        val adapter = SelfTextAdapter()
        dataList = adapter.bindData(applicationContext, dataList) as MutableList<Any>
        recyclerView.adapter = adapter

        recyclerView.setOnRefreshListener {
            Thread {
                Thread.sleep(1000)
                dataList.reverse()
                runOnUiThread { recyclerView.isRefreshing = false }
            }.start()

        }
        recyclerView.setOnLoadMoreListener(OnLoadMoreListener {
            recyclerView.loadMoreFail() 
        })

    ```



[1]:https://img.shields.io/:License-Apache%202.0-blue.svg
[2]:https://www.apache.org/licenses/LICENSE-2.0.html
[3]:https://img.shields.io/badge/API-14%2B-red.svg?style=flat
[4]:https://android-arsenal.com/api?level=16