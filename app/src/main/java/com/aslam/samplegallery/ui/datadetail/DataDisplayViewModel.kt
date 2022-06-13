package com.aslam.samplegallery.ui.datadetail

import android.content.ContentResolver
import android.database.Cursor
import android.provider.MediaStore
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.aslam.samplegallery.base.BaseViewModel
import com.aslam.samplegallery.data.DataItem
import com.aslam.samplegallery.di.Result
import java.lang.Exception
import java.util.ArrayList

class DataDisplayViewModel : BaseViewModel() {

    private lateinit var _allDataListLiveData: MutableLiveData<Result<List<DataItem>>>

    private var allDataListResultLiveData: MediatorLiveData<Result<List<DataItem>>> =
        MediatorLiveData()

    fun getAllDataList() = allDataListResultLiveData

    fun fetchAllDataList(contentResolver: ContentResolver, path: String, image: Boolean) {
        if (this::_allDataListLiveData.isInitialized)
            allDataListResultLiveData.removeSource(_allDataListLiveData)

        _allDataListLiveData = getAllImagesByFolder(contentResolver, path,image)!!
        allDataListResultLiveData.addSource(_allDataListLiveData) { outcome ->
            outcome.let {
                allDataListResultLiveData.value = it
            }

        }
    }

    fun getAllImagesByFolder(
        contentResolver: ContentResolver,
        path: String,
        isImage: Boolean
    ): MutableLiveData<Result<List<DataItem>>>? {
        var images: ArrayList<DataItem> = ArrayList<DataItem>()
        var cursor: Cursor?=null
        if (isImage) {
            val allImagessuri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            val projection = arrayOf(
                MediaStore.Images.ImageColumns.DATA, MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media.SIZE
            )
             cursor = contentResolver.query(
                allImagessuri, projection, MediaStore.Images.Media.DATA + " like ? ", arrayOf(
                    "%$path%"
                ), null
            )
        }else{
            val allVideosuri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
            val projection = arrayOf(
                MediaStore.Video.VideoColumns.DATA, MediaStore.Video.Media.DISPLAY_NAME,
                MediaStore.Video.Media.SIZE
            )
            cursor = contentResolver.query(
                allVideosuri, projection, MediaStore.Video.Media.DATA + " like ? ", arrayOf(
                    "%$path%"
                ), null
            )
        }
        try {
            cursor?.let {
                cursor?.moveToFirst()
                do {
                    var pic= DataItem(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)),
                    cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.SIZE)),
                        isImage)

                    images.add(pic)
                } while (cursor.moveToNext())
                cursor.close()
            }
            val reSelection: ArrayList<DataItem> = ArrayList<DataItem>()
            for (i in images.size - 1 downTo -1 + 1) {
                reSelection.add(images[i])
            }
            images = reSelection
        } catch (e: Exception) {
            e.printStackTrace()
        }
        var result=MutableLiveData<Result<List<DataItem>>>()
        result.postValue(Result.Success(images))
        return result
    }
}