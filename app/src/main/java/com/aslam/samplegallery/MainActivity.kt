package com.aslam.samplegallery

import com.aslam.samplegallery.base.BaseActivity
import com.aslam.samplegallery.base.BaseViewModel
import com.aslam.samplegallery.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun getNavHostId(): Int? {
        return R.id.nav_host_fragment
    }

    override fun getLayoutId() = R.layout.activity_main

    override fun hasConnectivity(connectivity: Boolean) {
        if (!connectivity)
            noConnectivity()
    }

    override fun getViewModel(): BaseViewModel? {
        return null
    }

}