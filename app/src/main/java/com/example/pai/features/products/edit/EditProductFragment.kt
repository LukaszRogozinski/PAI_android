package com.example.pai.features.products.edit


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.CursorAdapter
import android.widget.Spinner
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs

import com.example.pai.R
import com.example.pai.databinding.EditProductFragmentBinding
import kotlinx.android.synthetic.main.edit_product_fragment.view.*

/**
 * A simple [Fragment] subclass.
 */
class EditProductFragment : Fragment(), AdapterView.OnItemSelectedListener {

    private lateinit var binding: EditProductFragmentBinding

    private val viewModel: EditProductViewModel by lazy {
        val activity = requireNotNull(this.activity) {
        }
        ViewModelProviders.of(
            this,
            EditProductViewModel.Factory(
                activity.application,
                EditProductFragmentArgs.fromBundle(arguments!!).product,
                EditProductFragmentArgs.fromBundle(arguments!!).isNewProduct
            )
        )
            .get(EditProductViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.edit_product_fragment,
            container,
            false
        )
        binding.vm = viewModel
        setOnClickListeners()

        // viewModel.loadWarehouses()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        createArrayAdapterForSpinner(viewModel.warehouses, binding.warehouseTypeSpinner)
        createArrayAdapterForSpinner(viewModel.status, binding.statusSpinner)
        createArrayAdapterForSpinner(viewModel.productTypes, binding.productTypeSpinner)
        binding.productTypeSpinner.setSelection(2)
    }

    private fun setOnClickListeners() {
        binding.saveButton.setOnClickListener {
            viewModel.saveProduct()
        }
    }

    private fun <T> createArrayAdapterForSpinner(objects: List<T>, spinner: Spinner) {
        ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            objects
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }
        spinner.onItemSelectedListener = this
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        when {
            (p0 as View).warehouse_type_spinner != null -> {
                viewModel.productDtoToSave?.warehouseId = p0.getItemAtPosition(p2) as Int
            }
            (p0 as View).product_type_spinner != null -> {
                viewModel.productDtoToSave?.productTypeId = p0.getItemAtPosition(p2) as Int
            }
            (p0 as View).status_spinner != null -> {
                viewModel.productDtoToSave?.status = p0.getItemAtPosition(p2) as String
            }
        }
    }
}
