package com.aslam.samplegallery.ui.datalisting

import com.aslam.samplegallery.R
import com.aslam.samplegallery.base.BaseAdapter
import com.aslam.samplegallery.base.BaseViewHolder
import com.aslam.samplegallery.data.DataFolder
import com.aslam.samplegallery.databinding.ItemFolderBinding

class GalleryMainAdapter(
    private val clickListener: (result: DataFolder) -> Unit
): BaseAdapter<ItemFolderBinding>() {

    init {
        addData(ArrayList<DataFolder>())
    }

    fun addDataList(data: List<DataFolder>) {
        data.let {
            addData(it)
            notifyDataSetChanged()
        }
    }

    override fun viewHolder(layout: Int, binding: ItemFolderBinding): BaseViewHolder<ItemFolderBinding> {
        return GalleryMainViewHolder(binding,clickListener)
    }

    override fun layout(position: Int): Int {
        return R.layout.item_folder
    }
}