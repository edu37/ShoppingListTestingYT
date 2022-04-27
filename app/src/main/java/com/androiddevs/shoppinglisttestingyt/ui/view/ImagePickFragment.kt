package com.androiddevs.shoppinglisttestingyt.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.androiddevs.shoppinglisttestingyt.R
import com.androiddevs.shoppinglisttestingyt.databinding.FragmentImagePickBinding
import com.androiddevs.shoppinglisttestingyt.ui.viewmodel.ShoppingViewModel

class ImagePickFragment : Fragment(R.layout.fragment_image_pick) {

    private val mViewModel: ShoppingViewModel by viewModels()

    private var mBinding: FragmentImagePickBinding? = null
    private val binding get() = mBinding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentImagePickBinding.inflate(inflater, container, false)
        return binding.root
    }
}