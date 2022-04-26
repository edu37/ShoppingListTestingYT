package com.androiddevs.shoppinglisttestingyt.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.androiddevs.shoppinglisttestingyt.R
import com.androiddevs.shoppinglisttestingyt.databinding.FragmentAddShoppingItemBinding

class AddShoppingItemFragment : Fragment(R.layout.fragment_add_shopping_item) {

    private val mViewModel: ShoppingViewModel by viewModels()

    private var mBinding: FragmentAddShoppingItemBinding? = null
    private val binding get() = mBinding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentAddShoppingItemBinding.inflate(inflater, container, false)
        return binding.root
    }
}