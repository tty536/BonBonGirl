package com.qiufang.bonbon.ui.video

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.VideoView
import com.qiufang.bonbon.R
import com.qiufang.bonbon.databinding.ActivityVideoPlayerBinding
import com.qiufang.bonbon.databinding.FragmentSlideshowBinding

class VideoPlayerActivity : AppCompatActivity() {
    private var _binding: ActivityVideoPlayerBinding? = null
    val binding get() = _binding!!
    private var videoPlayer : VideoView? = null
    private var videoUrl : String? =  null
    private var videoTitle : String? = null
    private var videoIntroduce :String? = null
    private var btnStart : Button? = null
    private var btnStop : Button? = null
    private var btnRestart : Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityVideoPlayerBinding.inflate(layoutInflater)
        videoUrl = intent.getStringExtra("video_url")
        videoTitle = intent.getStringExtra("video_title")
        videoIntroduce = intent.getStringExtra("video_introduce")
        btnStart = binding.start
        btnStop = binding.btnStop
        btnRestart = binding.btnRestart


        videoPlayer = binding.videoPlayer
        val uri = Uri.parse(videoUrl)
        videoPlayer!!.setVideoURI(uri)
        videoPlayer!!.seekTo(1)
//        videoPlayer!!.start()
        btnStart!!.setOnClickListener {
            videoPlayer!!.start()
        }
        btnStop!!.setOnClickListener {
            videoPlayer!!.pause()
        }
        btnStop!!.setOnClickListener {
            videoPlayer!!.resume()
        }
        this.title = videoTitle
        setContentView(binding.root)
    }
}