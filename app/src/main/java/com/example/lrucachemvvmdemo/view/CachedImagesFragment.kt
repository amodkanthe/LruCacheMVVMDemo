package com.example.lrucachemvvmdemo.view

import android.R.attr
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.example.lrucachemvvmdemo.R
import com.example.lrucachemvvmdemo.RandomDogApp
import com.example.lrucachemvvmdemo.databinding.FragmentCachedImagesBinding
import com.example.lrucachemvvmdemo.databinding.FragmentRandomDogBinding
import com.example.omdbdemo.util.decodeSampledBitmapFromResource
import com.example.omdbdemo.viewmodels.RandomDogViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.net.URI
import android.provider.MediaStore

import android.graphics.Bitmap

import android.R.attr.data
import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import com.example.omdbdemo.util.resizeBitmap
import android.os.Build

import android.content.ContentResolver
import android.graphics.ImageDecoder
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lrucachemvvmdemo.adapter.ImageCacheAdapter
import com.example.omdbdemo.util.Status
import com.example.omdbdemo.viewmodels.CachedImagesViewModel
import java.lang.Exception


@AndroidEntryPoint
class CachedImagesFragment : Fragment() {

    private lateinit var binding: FragmentCachedImagesBinding

    private val cachedImagesViewModel: CachedImagesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate<FragmentCachedImagesBinding>(
            inflater,
            R.layout.fragment_cached_images,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cachedImagesViewModel.fetchImages()
        binding.btnClear.setOnClickListener {
            cachedImagesViewModel.fetchImages()
            RandomDogApp.diskLruImageCache?.clearCache()
            val app : RandomDogApp = context?.applicationContext as RandomDogApp
            app.intitCache()
        }
        binding.rvImages.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
        setUpObserver()
    }

    fun setUpObserver(){
        cachedImagesViewModel.bitmaps.observe(viewLifecycleOwner,{
            when (it.status) {
                Status.SUCCESS -> {
                    binding.rvImages.adapter = ImageCacheAdapter(it.data);
                }
                Status.LOADING -> {
                }
                Status.ERROR -> {
                }
            }
        })
    }

    companion object {
         @JvmStatic
        fun newInstance() =
            CachedImagesFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}