package com.aslam.samplegallery.ui.preview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.ViewCompat
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.aslam.samplegallery.R
import com.aslam.samplegallery.data.DataFolder
import com.aslam.samplegallery.data.DataItem
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class ImagesPagerAdapter(allImages: List<DataItem>,
                         private val clickListener: (result: DataItem) -> Unit) : PagerAdapter() {
    var allImages = allImages
    override fun getCount(): Int {
        return allImages.size
    }

    override fun instantiateItem(containerCollection: ViewGroup, position: Int): Any {
        val layoutinflater =
            containerCollection.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view: View = layoutinflater.inflate(R.layout.picture_browser_pager, null)
        val image = view.findViewById<ImageView>(R.id.image)
        ViewCompat.setTransitionName(image, position.toString() + "picture")
        val pic: DataItem = allImages.get(position)
        Glide.with(view.context)
            .load(pic.itemPath)
            .apply(RequestOptions().fitCenter())
            .into(image)
        image.setOnClickListener(View.OnClickListener {
            clickListener(pic)
        })
        (containerCollection as ViewPager).addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
                (container as ViewPager).removeView(`object` as View?)
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object` as View
    }
}