package com.aslam.samplegallery.ui.datadetail

import android.os.Bundle
import com.aslam.samplegallery.R
import com.aslam.samplegallery.base.BaseFragment
import com.aslam.samplegallery.base.BaseViewModel
import com.aslam.samplegallery.data.DataItemList
import com.aslam.samplegallery.databinding.FragmentDataDisplayBinding
import com.aslam.samplegallery.di.Result
import com.aslam.samplegallery.ui.preview.PreviewFragmentArgs
import com.aslam.samplegallery.ui.preview.video.VideoFragmentArgs
import org.koin.androidx.viewmodel.ext.android.viewModel

class DataDisplayFragment : BaseFragment<FragmentDataDisplayBinding>() {

    private val viewModel by viewModel<DataDisplayViewModel>()

    val dataDetail by lazy {
        DataDisplayFragmentArgs.fromBundle(requireArguments()).dataDetail
    }

    private val dataDisplayAdapter by lazy {
        DataDisplayAdapter { dataItem, position ->
            if (dataItem.isImage) {
                val bundle = Bundle()
                val dataItemList = DataItemList()
                dataItemList.addAll((viewModel.getAllDataList().value as Result.Success).data)
                bundle.putParcelable("dataItemList", dataItemList)
                bundle.putInt("position", position)
                val args = PreviewFragmentArgs.fromBundle(bundle)
                navigateToDestination(R.id.actionDataDisplayToPreview, args.toBundle())
            }else{
                val bundle = Bundle()
                bundle.putParcelable("dataItem", dataItem)
                val args = VideoFragmentArgs.fromBundle(bundle)
                navigateToDestination(R.id.actionDataDisplayToVideo, args.toBundle())

            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.data = dataDetail
    }

    override fun getLayoutId() = R.layout.fragment_data_display


    override fun getViewModel(): BaseViewModel? {
        return viewModel
    }

    override fun initFragment() {
        if (binding.lifecycleOwner == null) {
            binding.apply {
                lifecycleOwner = this@DataDisplayFragment
                viewModel = this@DataDisplayFragment.viewModel
                recycler.hasFixedSize()
                activity?.let {
                    viewModel!!.fetchAllDataList(
                        it.getContentResolver(),
                        dataDetail.path,
                        dataDetail.isImage
                    )
                }
                adapter = dataDisplayAdapter
            }
        }

        binding.lifecycleOwner = this
    }


}