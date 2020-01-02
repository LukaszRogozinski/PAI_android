package com.example.pai.features.users.changepassword


import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.pai.MainNavigationDirections
import org.koin.androidx.viewmodel.ext.android.viewModel

import com.example.pai.R
import com.example.pai.databinding.ChangeUserPasswordFragmentBinding
import com.example.pai.utils.Utils

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
        val user = args.user
        val isMyAccount = args.isMyAccount
        viewModel.user = user
        viewModel.isMyAccount = isMyAccount
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
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.logged_user_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.userDetailFragmentMenu -> {
                val loggedUser = viewModel.getLoggedUser()
                val action = MainNavigationDirections.actionGlobalUserDetailFragment(loggedUser)
                findNavController(this).navigate(action)
                true
            }
            R.id.logout_menu -> {
                Utils.logOutDialog(requireActivity(), viewModel.sessionRepository, this)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


}
