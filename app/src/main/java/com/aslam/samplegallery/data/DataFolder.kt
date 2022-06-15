package com.aslam.samplegallery.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DataFolder(val path:String, val FolderName:String, var firstPic:String, var isImage:Boolean ):Parcelable{
var numberOfPics=0
}
