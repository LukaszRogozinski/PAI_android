package com.example.pai.features.users.detail


import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment
import com.example.pai.DataBinderMapperImpl

import com.example.pai.R
import com.example.pai.databinding.UserDetailFragmentBinding
import com.example.pai.domain.User
import com.shreyaspatil.MaterialDialog.MaterialDialog
import timber.log.Timber

/**
 * A simple [Fragment] subclass.
 */
class UserDetailFragment : Fragment() {

    private lateinit var binding: UserDetailFragmentBinding

    private lateinit var user: User

    private val viewModel: UserDetailViewModel by lazy {
        ViewModelProviders.of(
            this,
            UserDetailViewModel.Factory(
                user
            )
        )
            .get(UserDetailViewModel::class.java)
    }

    @SuppressLint("RestrictedApi")
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
        binding.vm = viewModel


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

        viewModel.showDeleteDialog.observe(viewLifecycleOwner, Observer {
            if (it) {
                val mDialog: MaterialDialog = MaterialDialog.Builder(requireActivity())
                    .setTitle("Delete?")
                    .setMessage("Are you sure want to delete this user?")
                    .setCancelable(true)
                    .setPositiveButton(
                        "Delete", R.drawable.ic_delete_24px
                    ) { dialogInterface, _ ->
                        viewModel.deleteUser()
                        dialogInterface?.dismiss()
                    }
                    .setNegativeButton(
                        "Cancel", R.drawable.ic_close_24px
                    ) { dialogInterface, _ -> dialogInterface?.dismiss() }
                    .build()

                mDialog.show()
            }
        })

        return binding.root
    }


}
