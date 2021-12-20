package com.example.lrucachemvvmdemo.view

import android.graphics.Bitmap
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.lrucachemvvmdemo.R
import com.example.lrucachemvvmdemo.databinding.FragmentRandomDogBinding
import com.example.omdbdemo.viewmodels.RandomDogViewModel
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.lrucachemvvmdemo.cache.DiskLruImageCache
import com.example.omdbdemo.util.Status
import com.example.omdbdemo.util.resizeBitmap

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RandomDogFragment : Fragment() {

    private val randomDogViewModel: RandomDogViewModel by viewModels()

    private lateinit var binding: FragmentRandomDogBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate<FragmentRandomDogBinding>(
            inflater,
            R.layout.fragment_random_dog,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpObserver()
        binding.btnGenerate.setOnClickListener{
            randomDogViewModel.fetchDetails()
        }
    }

    fun setUpObserver() {
        randomDogViewModel.dogDetail.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    binding.pbRandom.visibility = View.GONE
                   randomDogViewModel.fetchBitmap(it.data);
                }
                Status.LOADING -> {
                    binding.pbRandom.visibility = View.VISIBLE
                }
                Status.ERROR -> {
                    binding.pbRandom.visibility = View.GONE
                }
            }
        })

        randomDogViewModel.dogBitmap.observe(viewLifecycleOwner, Observer {
            val scaledBitmap =  resizeBitmap(it, 240, 240);
            binding.ivDogPhoto.setImageBitmap(scaledBitmap)
        })
    }
    companion object {
        @JvmStatic
        fun newInstance() =
            RandomDogFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}