package com.aslam.samplegallery.ui.preview

import android.annotation.SuppressLint
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.aslam.samplegallery.R
import com.aslam.samplegallery.base.BaseFragment
import com.aslam.samplegallery.base.BaseViewModel
import com.aslam.samplegallery.databinding.FragmentPreviewBinding


class PreviewFragment : BaseFragment<FragmentPreviewBinding>() {

    private var viewVisibilitylooper = 0
    private var pagingImages: ImagesPagerAdapter? = null
    private var previousSelected = -1

    val dataItemList by lazy {
        PreviewFragmentArgs.fromBundle(requireArguments()).dataItemList
    }
    val position by lazy {
        PreviewFragmentArgs.fromBundle(requireArguments()).position
    }

    override fun getLayoutId() = R.layout.fragment_preview

    override fun getViewModel(): BaseViewModel? = null

    override fun initFragment() {
        binding.lifecycleOwner = this
        binding.apply {
            viewVisibilitylooper = 0
            pagingImages = ImagesPagerAdapter(dataItemList) { dataItem ->
                if (indicatorRecycler.visibility == View.GONE) {
                    indicatorRecycler.visibility = View.VISIBLE
                } else {
                    indicatorRecycler.visibility = View.GONE
                }
            }
            imagePager.adapter = pagingImages
            imagePager.offscreenPageLimit = 3
            imagePager.currentItem =
                position //displaying the image at the current position passed by the ImageDisplay Activity

            indicatorRecycler.hasFixedSize()
            indicatorRecycler.layoutManager =
                GridLayoutManager(context, 1, RecyclerView.HORIZONTAL, false)
            val indicatorAdapter = PreviewPagerImageIndicator { position, dataItem ->

                //the below lines of code highlights the currently select image in  the indicatorRecycler with respect to the viewPager position
                if (previousSelected != -1) {
                    dataItemList.get(previousSelected).selected = false
                    previousSelected = position
                    indicatorRecycler.adapter!!.notifyDataSetChanged()
                } else {
                    previousSelected = position
                }

                imagePager.currentItem = position

            }

            indicatorRecycler.adapter = indicatorAdapter
            indicatorAdapter.totalCount = dataItemList.size
            indicatorAdapter.addDataList(dataItemList)
            dataItemList.get(position).selected = true
            previousSelected = position
            indicatorAdapter.notifyDataSetChanged()
            indicatorRecycler.scrollToPosition(position)
            imagePager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
                ) {
                }

                @SuppressLint("NotifyDataSetChanged")
                override fun onPageSelected(position: Int) {
                    if (previousSelected != -1) {
                        dataItemList.get(previousSelected).selected = false
                        previousSelected = position
                        dataItemList.get(position).selected = true
                        indicatorRecycler.adapter!!.notifyDataSetChanged()
                        indicatorRecycler.scrollToPosition(position)
                    } else {
                        previousSelected = position
                        dataItemList.get(position).selected = true
                        indicatorRecycler.adapter!!.notifyDataSetChanged()
                        indicatorRecycler.scrollToPosition(position)
                    }
                }

                override fun onPageScrollStateChanged(state: Int) {
                }

            })
        }
    }
}