package com.aslam.samplegallery.di

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.aslam.samplegallery.constants.KEY_PREF_NAME
import com.aslam.samplegallery.ui.datadetail.DataDisplayViewModel
import com.aslam.samplegallery.ui.datalisting.GalleryMainViewModel
import com.aslam.samplegallery.util.EncryptSharedPref
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val viewModelModule = module {

    viewModel {
        GalleryMainViewModel()
    }
    viewModel {
        DataDisplayViewModel()
    }
}

val sharedPrefModule = module {
    single { createMasterKey(androidContext()) }
    single { creteEncryptedSharedPref(androidContext(), get()) }
    single { EncryptSharedPref(get()) }
}


fun createMasterKey(context: Context): MasterKey {
    return MasterKey.Builder(context, MasterKey.DEFAULT_MASTER_KEY_ALIAS)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()
}

private fun creteEncryptedSharedPref(
    context: Context,
    masterKeyAlias: MasterKey
): SharedPreferences {

    return EncryptedSharedPreferences.create(
        context,
        KEY_PREF_NAME,
        masterKeyAlias,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )
}

