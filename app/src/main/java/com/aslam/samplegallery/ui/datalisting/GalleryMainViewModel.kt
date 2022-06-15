package com.aslam.samplegallery.ui.datalisting

import android.content.ContentResolver
import android.database.Cursor
import android.provider.MediaStore
import android.util.Log
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.aslam.samplegallery.base.BaseViewModel
import com.aslam.samplegallery.data.DataFolder
import com.aslam.samplegallery.di.Result
import java.lang.Exception
import java.util.ArrayList

class GalleryMainViewModel : BaseViewModel() {

    private lateinit var _allDataListLiveData: MutableLiveData<Result<List<DataFolder>>>

    private var allDataListResultLiveData: MediatorLiveData<Result<List<DataFolder>>> =
        MediatorLiveData()

    fun getAllDataList() = allDataListResultLiveData

    fun fetchAllDataList(contentResolver: ContentResolver) {
        if (this::_allDataListLiveData.isInitialized)
            allDataListResultLiveData.removeSource(_allDataListLiveData)

        _allDataListLiveData = getPicturePaths(contentResolver)!!
        allDataListResultLiveData.addSource(_allDataListLiveData) { outcome ->
            outcome.let {
                allDataListResultLiveData.value = it
            }

        }
    }

    private fun getPicturePaths(contentResolver: ContentResolver): MutableLiveData<Result<List<DataFolder>>>? {
        val picFolders: ArrayList<DataFolder> = ArrayList<DataFolder>()
        val picPaths = ArrayList<String>()
        addImagesAndVideosToList(contentResolver, picFolders, picPaths, true)
        picPaths.clear()
        addImagesAndVideosToList(contentResolver, picFolders, picPaths,false)

        var result = MutableLiveData<Result<List<DataFolder>>>()
        result.postValue(Result.Success(picFolders))
        return result
    }

    private fun addImagesAndVideosToList(
        contentResolver: ContentResolver,
        picFolders: ArrayList<DataFolder>,
        picPaths: ArrayList<String>,
        imageRequest: Boolean
    ) {
        var cursor: Cursor? = null
        var numberOfImageFile=picFolders.size
        if (imageRequest) {
            val allImagesuri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            val projection = arrayOf(
                MediaStore.Images.ImageColumns.DATA, MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME, MediaStore.Images.Media.BUCKET_ID
            )
            cursor =
                contentResolver.query(allImagesuri, projection, null, null, null)!!
        } else {
            val allVideosuri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
            val projection = arrayOf(
                MediaStore.Video.VideoColumns.DATA, MediaStore.Video.Media.DISPLAY_NAME,
                MediaStore.Video.Media.BUCKET_DISPLAY_NAME, MediaStore.Video.Media.BUCKET_ID
            )
            cursor =
                contentResolver.query(allVideosuri, projection, null, null, null)!!

        }
        try {
            if (cursor != null) {
                cursor.moveToFirst()
            }
            do {
                var folds: DataFolder? = null
                val name =
                    cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME))
                val folder =
                    cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME))
                val datapath =
                    cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA))

                //String folderpaths =  datapath.replace(name,"");
                var folderpaths = datapath.substring(0, datapath.lastIndexOf("$folder/"))
                folderpaths = "$folderpaths$folder/"
                if (!picPaths.contains(folderpaths)) {
                    picPaths.add(folderpaths)
                    folds = DataFolder(folderpaths, folder, datapath, imageRequest)
                    folds.numberOfPics = folds.numberOfPics + 1
                    picFolders.add(folds)
                } else {
                    for (i in picFolders.indices) {
                        if (picFolders[i].path == folderpaths) {
                            picFolders[i].firstPic = datapath
                            picFolders[i].numberOfPics++
                        }
                    }
                }
            } while (cursor.moveToNext())
            cursor.close()

        } catch (e: Exception) {
            e.printStackTrace()
        }
        if (picFolders.size > 0) {
            if (imageRequest) {

                val dataFolder = DataFolder("", "All Images", picFolders[0].firstPic, imageRequest)
                dataFolder.numberOfPics =numberOfFiles(picFolders,numberOfImageFile)

                picFolders.add(0, dataFolder)
            }else{
                val dataFolder = DataFolder("", "All Videos", picFolders[numberOfImageFile].firstPic, imageRequest)
                dataFolder.numberOfPics =numberOfFiles(picFolders,numberOfImageFile)

                picFolders.add(numberOfImageFile, dataFolder)
            }
        }
    }

    fun numberOfFiles(picFolders: ArrayList<DataFolder>, numberOfImageFile: Int):Int{
        var numberOfFiles=0
        for (i in numberOfImageFile until picFolders.size) {
            numberOfFiles += picFolders[i].numberOfPics
            Log.d(
                "picture folders",
                picFolders[i].FolderName
                    .toString() + " and path = " + picFolders[i].path + " " + picFolders[i].numberOfPics
            )
        }
        return numberOfFiles
    }
}