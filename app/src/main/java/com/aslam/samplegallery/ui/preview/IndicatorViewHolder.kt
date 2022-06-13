package com.aslam.samplegallery.ui.preview

import android.view.View
import android.widget.ImageView
import androidx.cardview.widget.CardView
import com.aslam.samplegallery.R
import com.aslam.samplegallery.base.BaseViewHolder
import com.aslam.samplegallery.bindings.bindRecyclerViewAdapter
import com.aslam.samplegallery.data.DataItem
import com.aslam.samplegallery.databinding.IndicatorHolderBinding

class IndicatorViewHolder(
    view: IndicatorHolderBinding,
    val clickListener: (itemPosition:Int,result: DataItem) -> Unit
): BaseViewHolder<IndicatorHolderBinding>(view) {

    private lateinit var data: DataItem
    private var itemPosition: Int=0

    override fun bindData(data: Any, position: Int) {

        if (data is DataItem) {
            this.data = data
            this.itemPosition=position
            binding().data = data
        }
    }

    override fun onClick(v: View?) {
        data.selected=true
        clickListener(itemPosition,data)
    }

    override fun onLongClick(v: View?): Boolean {
        return false
    }
}