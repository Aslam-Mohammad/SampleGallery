package com.aslam.samplegallery.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DataItem(val itemName:String, val itemPath:String, val itemSize:String,val isImage:Boolean):Parcelable{
    var selected:Boolean=false
}
