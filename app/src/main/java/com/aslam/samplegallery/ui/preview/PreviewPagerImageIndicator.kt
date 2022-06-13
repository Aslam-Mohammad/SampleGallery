package com.aslam.samplegallery.ui.preview

import com.aslam.samplegallery.R
import com.aslam.samplegallery.base.BaseAdapter
import com.aslam.samplegallery.base.BaseViewHolder
import com.aslam.samplegallery.data.DataItem
import com.aslam.samplegallery.databinding.IndicatorHolderBinding
import java.util.ArrayList

class PreviewPagerImageIndicator(
    private val clickListener:(itemPosition:Int,result: DataItem) -> Unit

): BaseAdapter<IndicatorHolderBinding>() {
    init {
        addData(ArrayList<DataItem>())
    }
    fun addDataList(data: List<DataItem>) {
        data.let {
            addData(it)
            notifyDataSetChanged()
        }
    }

    override fun viewHolder(layout: Int, binding: IndicatorHolderBinding): BaseViewHolder<IndicatorHolderBinding> {
        return IndicatorViewHolder(binding,clickListener)
    }

    override fun layout(position: Int): Int {
        return R.layout.indicator_holder
    }
}