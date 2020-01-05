package com.example.pai.features.users


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.pai.MainNavigationDirections
import com.example.pai.R
import com.example.pai.databinding.UsersFragmentBinding
import com.example.pai.utils.Utils
import com.shreyaspatil.MaterialDialog.MaterialDialog
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass.
 */
class UsersFragment : Fragment() {

    private lateinit var binding: UsersFragmentBinding

    private val viewModel: UsersViewModel by viewModel()

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

        viewModel.loadUsersFromNetwork()

        setObservers()
        setHasOptionsMenu(true)
        return binding.root
    }

    private fun setObservers() {
        viewModel.eventNetworkError.observe(viewLifecycleOwner, Observer<Boolean> {
            if (it) {
                onNetworkError()
            }
        })

        viewModel.navigateToSelectedUser.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                val action = UsersFragmentDirections.actionUsersFragmentToUserDetailFragment(it)
                    .setIsMyAccount(false)
                findNavController(this).navigate(action)
                viewModel.navigateToSelectedUserDone()
            }
        })

        viewModel.navigateToNewUser.observe(viewLifecycleOwner, Observer {
            if (it) {
                val action = UsersFragmentDirections.actionUsersFragmentToUserEditFragment(false)
                    .setUser(null)
                findNavController(this).navigate(action)
                viewModel.navigateToNewUserDone()
            }
        })
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(
            this,
            Utils.onBackPressed(requireActivity(), viewModel.sessionRepository, this)
        )
    }

    private fun onNetworkError() {
        if (!viewModel.isNetworkErrorShown.value!!) {
            Toast.makeText(activity, "Network Error", Toast.LENGTH_LONG).show()
            viewModel.onNetworkErrorShown()
        }
    }
}
