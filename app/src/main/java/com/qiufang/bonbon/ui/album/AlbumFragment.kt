package com.qiufang.bonbon.ui.album

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.qiufang.bonbon.ui.music.MusicActivity
import com.qiufang.bonbon.databinding.FragmentHomeBinding
import com.qiufang.bonbon.utils.Constants
import com.qiufang.bonbon.utils.LogUtil

class AlbumFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val TAG = this.javaClass.toString()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(AlbumViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val albumList :RecyclerView = binding.listAlbum
        val banner : ImageView = binding.imgBanner


//        val layoutManager  = LinearLayoutManager(this.context)
//        layoutManager.orientation = LinearLayoutManager.VERTICAL

        homeViewModel.getAlbum()
        homeViewModel.albumList.observe(viewLifecycleOwner, Observer {
            val albumListData = it  ?: return@Observer

            val layoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
            albumList.layoutManager  = layoutManager
            var adapter = AlbumListAdapter(null)
            adapter = if (albumListData.isNotEmpty()){
                AlbumListAdapter(albumListData)
            }else{
                AlbumListAdapter(Constants.getEmptyList())
            }

            adapter.setListener(object : AlbumListAdapter.OnItemClickListener{
                override fun onItemClick(album: Album) {
                    super.onItemClick(album)
                    gotoMusicActivity(album)
                }
            })
            albumList.adapter = adapter
        })


//        val textView: TextView = binding.textHome
//
//        homeViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }
        LogUtil.d(TAG,"Fragment CreateView...")
        return root
    }

    fun gotoMusicActivity(album: Album){
        val intent = Intent(context, MusicActivity::class.java)
        intent.putExtra("album_id",album.id)
        intent.putExtra("album_groupName",album.group)
        intent.putExtra("album_cover",album.imageUrl)
        intent.putExtra("album_name",album.name)
        intent.putExtra("album_date",album.date)
        context?.startActivity(intent)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        LogUtil.d(TAG,"Fragment Destroy View...")
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        LogUtil.d(TAG,"Fragment Attach...")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LogUtil.d(TAG,"Fragment Create...")
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        LogUtil.d(TAG,"Fragment Start...")
    }

    override fun onResume() {
        super.onResume()
        LogUtil.d(TAG,"Fragment Resume...")
    }

    override fun onDetach() {
        super.onDetach()
        LogUtil.d(TAG,"Fragment Detach...")
    }
}