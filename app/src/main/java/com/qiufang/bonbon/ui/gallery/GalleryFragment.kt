package com.qiufang.bonbon.ui.gallery

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.qiufang.bonbon.databinding.FragmentGalleryBinding
import com.qiufang.bonbon.ui.photo.PhotoActivity
import com.qiufang.bonbon.utils.Constants

class GalleryFragment : Fragment() {

    private var _binding: FragmentGalleryBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val galleryViewModel =
            ViewModelProvider(this).get(GalleryViewModel::class.java)

        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val galleryList = binding.gallery

        galleryViewModel.getGallery()
        galleryViewModel.galleryList.observe(viewLifecycleOwner, Observer {
            val albumListData = it  ?: return@Observer

            val layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
            galleryList.layoutManager  = layoutManager

            val adapter = GalleryAdapter()
            if (albumListData.isNotEmpty()){
                adapter.galleryList = albumListData
            }else{
                adapter.galleryList = Constants.getEmptyGalleryList()
            }

            adapter.setListener(object : GalleryAdapter.OnItemClickListener{
                override fun onItemClick(gallery: Gallery) {
                    super.onItemClick(gallery)
                    gotoPhotoActivity(gallery)
                }
            })
            galleryList.adapter = adapter
        })
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun gotoPhotoActivity(gallery: Gallery){
        val intent = Intent(context, PhotoActivity::class.java)
        intent.putExtra("gallery_id",gallery.id)
        intent.putExtra("gallery_name",gallery.name)
        context?.startActivity(intent)
    }
}