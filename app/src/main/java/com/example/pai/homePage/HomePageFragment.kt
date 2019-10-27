package com.example.pai.homePage


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.pai.R
import com.example.pai.databinding.HomePageFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass.
 */
class HomePageFragment : Fragment() {

    private lateinit var binding: HomePageFragmentBinding

    private val viewModel: HomePageViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.home_page_fragment,
            container,
            false
        )
        binding.vm = viewModel
        binding.lifecycleOwner = this

        // Inflate the layout for this fragment
        return binding.root
    }


}
