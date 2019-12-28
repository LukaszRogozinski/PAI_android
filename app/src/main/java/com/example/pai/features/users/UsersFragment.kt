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
//    {
//        ViewModelProviders.of(this, UsersViewModel.Factory())
//            .get(UsersViewModel::class.java)
//    }

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
                val action = UsersFragmentDirections.actionUsersFragmentToUserDetailFragment(it)
                findNavController(this).navigate(action)
                viewModel.navigateToSelectedUserDone()
            }
        })

        viewModel.navigateToNewUser.observe(viewLifecycleOwner, Observer {
            if(it) {
                val action = UsersFragmentDirections.actionUsersFragmentToUserEditFragment().setUser(null)
                findNavController(this).navigate(action)
                viewModel.navigateToNewUserDone()
            }
        })

        setHasOptionsMenu(true)
        return binding.root
    }

//    @SuppressLint("RestrictedApi")
//    fun showLogoutDialog() {
//        val mDialog: MaterialDialog = MaterialDialog.Builder(requireActivity())
//            .setTitle("Logout")
//            .setMessage("Are you sure want to logout?")
//            .setCancelable(true)
//            .setPositiveButton(
//                "Logout", R.drawable.ic_delete_24px
//            ) { dialogInterface, _ ->
//                viewModel.logout()
//                dialogInterface?.dismiss()
//                requireActivity().finish()
//            }
//            .setNegativeButton(
//                "Cancel", R.drawable.ic_close_24px
//            ) { dialogInterface, _ -> dialogInterface?.dismiss() }
//            .build()
//
//        mDialog.show()
//    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.logged_user_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.userDetailFragmentMenu -> {
                val loggedUser = viewModel.getLoggedUser()
                val action = UsersFragmentDirections.actionUsersFragmentToUserDetailFragment(loggedUser.user)
                findNavController(this).navigate(action)
                true
            }
            R.id.logout_menu -> {
                Utils.logOutDialog(requireActivity(), viewModel.sessionRepository)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(
            this,
            Utils.onBackPressed(requireActivity(), viewModel.sessionRepository)
        )
    }

    private fun onNetworkError() {
        if (!viewModel.isNetworkErrorShown.value!!) {
            Toast.makeText(activity, "Network Error", Toast.LENGTH_LONG).show()
            viewModel.onNetworkErrorShown()
        }
    }
}
