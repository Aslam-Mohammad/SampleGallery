package com.aslam.samplegallery.bindings

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.aslam.samplegallery.base.BaseAdapter
import com.aslam.samplegallery.data.DataFolder
import com.aslam.samplegallery.data.DataItem
import com.aslam.samplegallery.ui.datalisting.GalleryMainAdapter
import com.aslam.samplegallery.di.Result
import com.aslam.samplegallery.ui.datadetail.DataDisplayAdapter


@BindingAdapter("adapter")
fun bindRecyclerViewAdapter(recyclerView: RecyclerView, adapter: BaseAdapter<*>) {
    recyclerView.adapter = recyclerView.adapter ?: adapter

}

@BindingAdapter("loadData")
fun bindRecyclerDataList(recyclerView: RecyclerView, response: Result<List<DataFolder>>?) {
    response?.let {
        if (it is Result.Success) {
            it.data.let { dataResponse ->
                val adapter = recyclerView.adapter as? GalleryMainAdapter
                adapter?.totalCount = dataResponse.size
                adapter?.addDataList(dataResponse)
            }
        }
    }

}

@BindingAdapter("loadListData")
fun bindRecyclerDataDisplay(recyclerView: RecyclerView, response: Result<List<DataItem>>?) {
    response?.let {
        if (it is Result.Success) {
            it.data.let { dataResponse ->
                val adapter = recyclerView.adapter as? DataDisplayAdapter
                adapter?.totalCount = dataResponse.size
                adapter?.addDataList(dataResponse)
            }
        }
    }

}