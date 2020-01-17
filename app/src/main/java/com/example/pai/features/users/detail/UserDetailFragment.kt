package com.example.pai.features.users.detail

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.navArgs
import com.example.pai.R
import com.example.pai.databinding.UserDetailFragmentBinding
import com.example.pai.domain.User
import com.shreyaspatil.MaterialDialog.MaterialDialog
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

/**
 * A simple [Fragment] subclass.
 */
class UserDetailFragment : Fragment() {

    private lateinit var binding: UserDetailFragmentBinding

    private var user: User? = null

    private val args by navArgs<UserDetailFragmentArgs>()
    private val viewModel: UserDetailViewModel by viewModel()

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
        user = args.user
        val isMyAccount = args.isMyAccount
        viewModel.setIsMyAccount(isMyAccount)
        viewModel.setUser(user)
        binding.item = viewModel.mutableUser.value
        binding.vm = viewModel

        setObservers()
        return binding.root
    }

    private fun setObservers() {
        viewModel.navigateToEditUser.observe(viewLifecycleOwner, Observer {
            if (it) {
                val action =
                    UserDetailFragmentDirections.actionUserDetailFragmentToUserEditFragment(
                        viewModel.getIsMyAccount()
                    ).setUser(
                        viewModel.getUser()
                    )
                NavHostFragment.findNavController(this).navigate(action)
                viewModel.navigateToEditUserDone()
            }
        })

        viewModel.deleteResponse.observe(viewLifecycleOwner, Observer<Boolean> {
            if (it) {
                Timber.i("item deleted")
                val action =
                    UserDetailFragmentDirections.actionUserDetailFragmentToUsersFragment()
                Toast.makeText(requireContext(), "item deleted", Toast.LENGTH_LONG).show()
                NavHostFragment.findNavController(this).navigate(action)
                Timber.i("navigate to users fragment")
                viewModel.onDeleteResponseFinish()
            }
        })

        viewModel.navigateToChangePasswordUser.observe(viewLifecycleOwner, Observer {
            if (it) {
                val action =
                    UserDetailFragmentDirections.actionUserDetailFragmentToChangeUserPasswordFragment(
                        viewModel.getUser(),
                        viewModel.getIsMyAccount()
                    )
                NavHostFragment.findNavController(this).navigate(action)
                viewModel.navigateToChangePasswordUserDone()
            }
        })

        viewModel.showDeleteDialog.observe(viewLifecycleOwner, Observer {
            if (it) {
                showDeleteDialog()
            }
        })
    }

    @SuppressLint("RestrictedApi")
    private fun showDeleteDialog() {
        val mDialog: MaterialDialog = MaterialDialog.Builder(requireActivity())
            .setTitle("Delete?")
            .setMessage("Are you sure want to delete this user?")
            .setCancelable(false)
            .setPositiveButton(
                "Delete", R.drawable.ic_delete_24px
            ) { dialogInterface, _ ->
                viewModel.deleteUser()
                dialogInterface?.dismiss()
            }
            .setNegativeButton(
                "Cancel", R.drawable.ic_close_24px
            ) { dialogInterface, _ ->
                run {
                    dialogInterface?.dismiss()
                    viewModel.showDeleteButtonClickedCanceled()
                }
            }
            .build()
        mDialog.show()
    }
}
