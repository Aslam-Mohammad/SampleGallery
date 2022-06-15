package com.aslam.samplegallery.ui.datalisting

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.aslam.samplegallery.R
import com.aslam.samplegallery.base.BaseFragment
import com.aslam.samplegallery.base.BaseViewModel
import com.aslam.samplegallery.databinding.FragmentGalleryMainBinding
import com.aslam.samplegallery.ui.datadetail.DataDisplayFragmentArgs
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel

class GalleryMainFragment : BaseFragment<FragmentGalleryMainBinding>() {

    private val viewModel by viewModel<GalleryMainViewModel>()
    private val MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1


    private val dataAdapter by lazy {
        GalleryMainAdapter { dataDetail ->
            val bundle = Bundle()
            bundle.putParcelable("dataDetail", dataDetail)
            val args = DataDisplayFragmentArgs.fromBundle(bundle)
            navigateToDestination(R.id.actionDataListToDetails, args.toBundle())
        }
    }

    override fun getLayoutId() = R.layout.fragment_gallery_main


    override fun getViewModel(): BaseViewModel? {
        return viewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    private fun isPermissionGranted(): Boolean {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
            != PackageManager.PERMISSION_GRANTED
        ) activity?.let {
            ActivityCompat.requestPermissions(
                it, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE
            )
            return false
        } else {
            return true
        }
        return false
    }


    override fun initFragment() {
        if (binding.lifecycleOwner == null) {
            binding.apply {
                lifecycleOwner = this@GalleryMainFragment
                viewModel = this@GalleryMainFragment.viewModel
                folderRecycler.hasFixedSize()
                permissionSetup()
//                if (isPermissionGranted()) {
//                    activity?.let { viewModel!!.fetchAllDataList(it.getContentResolver()) }
//                }
                adapter = dataAdapter
            }
        }
    }

    private fun permissionSetup() {
        val permission = ContextCompat.checkSelfPermission(
            requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE)

        if (permission != PackageManager.PERMISSION_GRANTED) {
            permissionsResultCallback.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
        } else {
            activity?.let { viewModel!!.fetchAllDataList(it.getContentResolver()) }
        }
    }

    private val permissionsResultCallback = registerForActivityResult(
        ActivityResultContracts.RequestPermission()){
        when (it) {
            true -> {
                activity?.let { viewModel!!.fetchAllDataList(it.getContentResolver()) }
            }
            false -> {
                Snackbar.make(binding.folderRecycler,"Permission denied",Snackbar.LENGTH_SHORT).show()
            }
        }
    }



}