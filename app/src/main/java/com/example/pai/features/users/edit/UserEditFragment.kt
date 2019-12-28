package com.example.pai.features.users.edit


import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import org.koin.androidx.viewmodel.ext.android.viewModel

import com.example.pai.R
import com.example.pai.databinding.UserEditFragmentBinding
import com.example.pai.generated.callback.OnClickListener
import com.example.pai.utils.Utils
import com.example.pai.utils.hideKeyboard
import kotlinx.android.synthetic.main.user_edit_fragment.*

/**
 * A simple [Fragment] subclass.
 */
class UserEditFragment : Fragment() {

    private lateinit var binding: UserEditFragmentBinding
    private val args by navArgs<UserEditFragmentArgs>()
    private val viewModel: UserEditViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.user_edit_fragment,
            container,
            false
        )
        val user = args.user
        viewModel.user = user
        binding.vm = viewModel

        viewModel.navBackToListOfUsers.observe(viewLifecycleOwner, Observer {
            if (it) {
                val action =
                    UserEditFragmentDirections.actionUserEditFragmentToUsersFragment()
                NavHostFragment.findNavController(this).navigate(action)
                Toast.makeText(requireContext(), "New user added", Toast.LENGTH_SHORT).show()
                val view = requireActivity().findViewById<View>(android.R.id.content)
                view?.hideKeyboard()
                viewModel.navBackToListOfUsersDone()
            }
        })

        viewModel.navBackToDetailUser.observe(viewLifecycleOwner, Observer {
            if (it) {
                val action =
                    UserEditFragmentDirections.actionUserEditFragmentToUserDetailFragment2()
                NavHostFragment.findNavController(this).navigate(action)
                Toast.makeText(requireContext(), "User edited", Toast.LENGTH_SHORT).show()
                val view = requireActivity().findViewById<View>(android.R.id.content)
                view?.hideKeyboard()
                viewModel.navBackToDetailUserDone()
            }
        })

        viewModel.selectRolesClicked.observe(viewLifecycleOwner, Observer {
            if (it) {
                selectRolesDialog()
                viewModel.selectRolesClickedDone()
            }
        })

        // Inflate the layout for this fragment
        return binding.root
    }


    private fun selectRolesDialog() {
        val result = mutableListOf<String>()
        val builder = AlertDialog.Builder(requireContext())
        val listArray = resources.getStringArray(R.array.roles)
        val userAuthorities = viewModel.rolesArray
        val checkList = arrayOf(
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false
        ).toBooleanArray()

        userAuthorities.forEach {
            val index = listArray.indexOf(it)
            checkList[index] = true
        }

        builder.setTitle("Select Roles")
        builder.setMultiChoiceItems(
            listArray,
            checkList
        ) { _, which, isChecked ->
            checkList[which] = isChecked
            val currentItem = listArray[which]
            Toast.makeText(requireContext(), "$currentItem is checked", Toast.LENGTH_SHORT)
                .show()
        }

        builder.setCancelable(true)

        builder.setPositiveButton("ok"
        ) { _, _ ->
            for (x in 0 until checkList.size step 1) {
                if (checkList[x]) {
                    result.add(listArray[x])
                }
            }
            viewModel.rolesArray = result
        }

        val dialog = builder.create()
        dialog.show()
    }

}


