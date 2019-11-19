package com.example.pai.features.users


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.pai.R
import com.example.pai.databinding.UsersFragmentBinding
import com.example.pai.utils.Utils

/**
 * A simple [Fragment] subclass.
 */
class UsersFragment : Fragment() {

    private lateinit var binding: UsersFragmentBinding

    private val viewModel: UsersViewModel by lazy {
        val activity = requireNotNull(this.activity) {
        }
        ViewModelProviders.of(this, UsersViewModel.Factory(activity.application))
            .get(UsersViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.users_fragment,
            container,
            false
        )
        binding.vm = viewModel
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(
            this,
            Utils.onBackPressedCallback(requireActivity(), requireContext())
        )
    }
}
