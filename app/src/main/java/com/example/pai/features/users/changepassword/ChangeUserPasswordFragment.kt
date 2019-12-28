package com.example.pai.features.users.changepassword


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.fragment.navArgs
import org.koin.androidx.viewmodel.ext.android.viewModel

import com.example.pai.R
import com.example.pai.databinding.ChangeUserPasswordFragmentBinding

/**
 * A simple [Fragment] subclass.
 */
class ChangeUserPasswordFragment : Fragment() {

    private lateinit var binding: ChangeUserPasswordFragmentBinding
    private val viewModel: ChangeUserPasswordViewModel by viewModel()
    private val args by navArgs<ChangeUserPasswordFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.change_user_password_fragment,
            container,
            false
        )
        val username = args.username
        viewModel.setUsername(username)
        binding.vm = viewModel

        viewModel.navBack.observe(viewLifecycleOwner, Observer {
            if (it) {
                val action =
                    ChangeUserPasswordFragmentDirections.actionChangeUserPasswordFragmentToUserDetailFragment()
                findNavController(this).navigate(action)
                Toast.makeText(requireContext(), "Password changed!", Toast.LENGTH_SHORT).show()
                viewModel.navBackDone()
            }
        })
        return binding.root
    }


}
