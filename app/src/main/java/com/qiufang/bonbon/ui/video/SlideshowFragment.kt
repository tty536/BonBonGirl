package com.qiufang.bonbon.ui.video

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.qiufang.bonbon.databinding.FragmentSlideshowBinding

class SlideshowFragment : Fragment() {

    private var _binding: FragmentSlideshowBinding? = null
    private val binding get() = _binding!!

    private var listVideo : RecyclerView? = null
    private var layoutManager : StaggeredGridLayoutManager? = null
    private var adapter : VideoListAdapter? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val slideshowViewModel =
            ViewModelProvider(this).get(SlideshowViewModel::class.java)

        _binding = FragmentSlideshowBinding.inflate(inflater, container, false)
        val root: View = binding.root

        listVideo = binding.listVideo
        layoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
        listVideo!!.layoutManager = layoutManager
        adapter = VideoListAdapter()

        slideshowViewModel.getVideo()
        slideshowViewModel.videoList.observe(viewLifecycleOwner, Observer {
            val listVideoData  = it ?: return@Observer
            adapter!!.setData(listVideoData)
            adapter!!.setListener(object :VideoListAdapter.OnItemClickListener{
                override fun onItemClick(video: Video) {
                    super.onItemClick(video)
                    gotoVideoPlayer(video)
                }
            })
            listVideo!!.adapter = adapter
        })

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    fun gotoVideoPlayer(video: Video){
        val intent  = Intent(this.context,VideoPlayerActivity::class.java)
        intent.putExtra("video_url",video.url)
        intent.putExtra("video_introduce",video.introduce)
        intent.putExtra("video_title",video.title)
        startActivity(intent)
    }
}