package com.aslam.samplegallery.ui.datadetail

import android.view.View
import com.aslam.samplegallery.base.BaseViewHolder
import com.aslam.samplegallery.data.DataItem
import com.aslam.samplegallery.databinding.ItemDataBinding

class DataDisplayViewHolder(
    view: ItemDataBinding,
    val clickListener: (result: DataItem,position:Int) -> Unit
): BaseViewHolder<ItemDataBinding>(view) {

    private lateinit var data: DataItem
    private  var itemPosition: Int=0

    override fun bindData(data: Any, position: Int) {

        if (data is DataItem) {
            this.data = data
            this.itemPosition=position
            binding().data = data
        }
    }

    override fun onClick(v: View?) {
        clickListener(data,itemPosition)
    }

    override fun onLongClick(v: View?): Boolean {
        return false
    }
}