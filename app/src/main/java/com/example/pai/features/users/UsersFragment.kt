package com.example.pai.features.users


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.example.pai.R
import com.example.pai.databinding.UsersFragmentBinding
import com.example.pai.utils.Utils

/**
 * A simple [Fragment] subclass.
 */
class UsersFragment : Fragment() {

    private lateinit var binding: UsersFragmentBinding

    private val viewModel: UsersViewModel by lazy {
        ViewModelProviders.of(this, UsersViewModel.Factory())
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
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.eventNetworkError.observe(viewLifecycleOwner, Observer<Boolean> {
            if (it) {
                onNetworkError()
            }
        })

        viewModel.navigateToSelectedUser.observe(viewLifecycleOwner, Observer {
            if(it != null){
                val action = UsersFragmentDirections.actionUsersFragmentToUserDetailFragment().setUser(it)
                findNavController(this).navigate(action)
                viewModel.navigateToSelectedUserDone()
            }
        })

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(
            this,
            Utils.onBackPressedCallback(requireActivity(), requireContext())
        )
    }

    private fun onNetworkError() {
        if (!viewModel.isNetworkErrorShown.value!!) {
            Toast.makeText(activity, "Network Error", Toast.LENGTH_LONG).show()
            viewModel.onNetworkErrorShown()
        }
    }
}
