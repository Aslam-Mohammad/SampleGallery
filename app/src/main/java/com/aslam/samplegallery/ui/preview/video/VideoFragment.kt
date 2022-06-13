package com.aslam.samplegallery.ui.preview.video

import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import com.aslam.samplegallery.R
import com.aslam.samplegallery.base.BaseFragment
import com.aslam.samplegallery.base.BaseViewModel
import com.aslam.samplegallery.databinding.FragmentPreviewBinding
import com.aslam.samplegallery.databinding.FragmentVideoBinding
import com.aslam.samplegallery.ui.preview.PreviewFragmentArgs
import com.aslam.samplegallery.widgets.PlayPauseListener
import com.aslam.samplegallery.widgets.ShowPlayPause

class VideoFragment : BaseFragment<FragmentVideoBinding>() {
    private var interfacePlayPause: ShowPlayPause?=null

    val dataItem by lazy {
        VideoFragmentArgs.fromBundle(requireArguments()).dataItem
    }
    override fun getLayoutId() = R.layout.fragment_video

    override fun getViewModel(): BaseViewModel? = null

    override fun initFragment() {
        binding.lifecycleOwner = this
        binding.apply {
            setVideoPlayer()
        }
    }

    private fun setVideoPlayer() {
        val controller = MediaController(activity)
        controller.setAnchorView(binding.videoView)
        controller.setMediaPlayer(binding.videoView)
//            binding.videoView.setMediaController(controller)
        binding.videoView.setVideoPath(dataItem.itemPath)
        binding.videoView.requestFocus()
        videoCompletionListener()
        binding.videoView.setOnPreparedListener(MediaPlayer.OnPreparedListener { mp ->
            val lp: ViewGroup.LayoutParams = binding.videoView.getLayoutParams()
            val videoWidth = mp.videoWidth.toFloat()
            val videoHeight = mp.videoHeight.toFloat()
            val viewWidth: Float = binding.videoView.getWidth().toFloat()
            lp.height = (viewWidth * (videoHeight / videoWidth)).toInt()
            binding.videoView.setLayoutParams(lp)
            controller.setPadding(0, 0, 0, 200)
            playVideo()
            mp.setOnInfoListener(MediaPlayer.OnInfoListener { mediaPlayer, what, i1 ->
                if (what == MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START) { // video started; hide the placeholder.
                    if (interfacePlayPause != null)
                        interfacePlayPause!!.showPlay()
                    return@OnInfoListener true
                }
                false
            })
        })
    }

    fun playVideo() {
        if (!binding.videoView.isPlaying()) {
            binding.videoView.start()
        }
    }
    fun stopVideo() {
        if (binding.videoView.isPlaying) {
            binding.videoView.pause()
        }
    }

    private fun videoCompletionListener() {
        binding.videoView.setOnCompletionListener(MediaPlayer.OnCompletionListener {
            if (interfacePlayPause != null)
                interfacePlayPause!!.pauseAndChange()
//            if (fileType.contentEquals(getString(R.string.audio))) {
//                setWaveForm(false)
//            }
        })
        binding.videoView.setPlayPauseListener(object : PlayPauseListener {
            override fun onPlay() {
            }

            override fun onPause() {
            }
        })
    }

    override fun onResume() {
        super.onResume()
        if (interfacePlayPause!=null)
            interfacePlayPause!!.showPause()
    }

    fun setInterface( showPlayPause: ShowPlayPause){
        interfacePlayPause=showPlayPause
    }


}