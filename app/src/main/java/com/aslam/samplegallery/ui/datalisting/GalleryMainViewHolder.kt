package com.aslam.samplegallery.ui.datalisting

import android.view.View
import com.aslam.samplegallery.base.BaseViewHolder
import com.aslam.samplegallery.data.DataFolder
import com.aslam.samplegallery.databinding.ItemFolderBinding

class GalleryMainViewHolder(
    view: ItemFolderBinding,
    val clickListener: (result: DataFolder) -> Unit
): BaseViewHolder<ItemFolderBinding>(view) {

    private lateinit var data: DataFolder

    override fun bindData(data: Any,position: Int) {

        if (data is DataFolder) {
            this.data = data
            binding().data = data
        }
    }

    override fun onClick(v: View?) {
        clickListener(data)
    }

    override fun onLongClick(v: View?): Boolean {
        return false
    }
}