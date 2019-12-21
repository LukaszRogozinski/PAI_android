package com.example.pai.features.users.detail


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.example.pai.DataBinderMapperImpl

import com.example.pai.R
import com.example.pai.databinding.UserDetailFragmentBinding
import com.example.pai.domain.User
import timber.log.Timber

/**
 * A simple [Fragment] subclass.
 */
class UserDetailFragment : Fragment() {

    private lateinit var binding: UserDetailFragmentBinding

    var user: User? = null

    private val viewModel: UserDetailViewModel by lazy {
        ViewModelProviders.of(
            this,
            UserDetailViewModel.Factory(
                user
            )
        )
            .get(UserDetailViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.user_detail_fragment,
            container,
            false
        )
        user = UserDetailFragmentArgs.fromBundle(arguments!!).user
        binding.item = viewModel.user
        return binding.root
    }


}
