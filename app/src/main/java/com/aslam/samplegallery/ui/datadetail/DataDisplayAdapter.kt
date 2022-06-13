package com.aslam.samplegallery.ui.datadetail

import com.aslam.samplegallery.R
import com.aslam.samplegallery.base.BaseAdapter
import com.aslam.samplegallery.base.BaseViewHolder
import com.aslam.samplegallery.data.DataItem
import com.aslam.samplegallery.databinding.ItemDataBinding

class DataDisplayAdapter(
    private val clickListener:(result: Int) -> Unit
):BaseAdapter<ItemDataBinding>() {
    init {
        addData(ArrayList<DataItem>())
    }
    fun addDataList(data: List<DataItem>) {
        data.let {
            addData(it)
            notifyDataSetChanged()
        }
    }

    override fun viewHolder(layout: Int, binding: ItemDataBinding): BaseViewHolder<ItemDataBinding> {
        return DataDisplayViewHolder(binding,clickListener)
    }

    override fun layout(position: Int): Int {
        return R.layout.item_data
    }
}